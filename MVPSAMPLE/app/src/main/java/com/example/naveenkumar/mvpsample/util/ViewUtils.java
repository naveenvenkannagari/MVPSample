package com.example.naveenkumar.mvpsample.util;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class ViewUtils {

	/*
	 * Removes the reference to the activity from every view in a view hierarchy (listeners, images
	 * etc.). This method should be called in the onDestroy() method of each activity
	 */
	public static void unbindReferences(View view) {
		try {
			if (view != null) {
				unbindViewReferences(view);
				if (view instanceof ViewGroup) {
					unbindViewGroupReferences((ViewGroup) view);
				}
			}
		} catch (Throwable e) {
		}
	}

	private static void unbindViewGroupReferences(ViewGroup viewGroup) {
		if (viewGroup instanceof ViewGroup) {
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View child = viewGroup.getChildAt(i);
				unbindViewReferences(child);
			}
			if (!(viewGroup instanceof AdapterView)) {
				viewGroup.removeAllViews();
			}
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	private static void unbindViewReferences(View view) {
		try {
			if (view instanceof AdapterView && !(view instanceof Spinner)) {
				((AdapterView) view).setOnItemClickListener(null);
			}
			else {
				view.setOnClickListener(null);
			}
			view.setOnCreateContextMenuListener(null);
			view.setOnFocusChangeListener(null);
			view.setOnKeyListener(null);
			view.setOnLongClickListener(null);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// set background to null
		Drawable d = view.getBackground();
		if (d != null) {
			d.setCallback(null);
		}

		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			d = imageView.getDrawable();
			if (d != null) {
				d.setCallback(null);
			}

			imageView.setImageDrawable(null);
			if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				imageView.setBackgroundDrawable(null);
			}
			else {
				imageView.setBackground(null);
			}
		}

		// destroy hybrid_webview_for_dsmpages
		else if (view instanceof WebView) {
			((WebView) view).stopLoading();
			((WebView) view).clearCache(true);
			((WebView) view).destroyDrawingCache();
			((WebView) view).setWebChromeClient(null);
			((WebView) view).setWebViewClient(null);
			((WebView) view).removeAllViews();
			((WebView) view).destroy();
		}
	}

	public static void setEnabledViewGroup(ViewGroup viewGroup, boolean enable) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View view  = viewGroup.getChildAt(i);
			if (view instanceof ViewGroup) {
				ViewUtils.setEnabledViewGroup((ViewGroup) view, enable);
			}
			view.setEnabled(enable);
			view.setClickable(enable);
		}
		viewGroup.setEnabled(enable);
		viewGroup.setClickable(enable);
	}

	public static int getStatusBarHeight(Resources resources) {
		int result = 0;
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = resources.getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	// ////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////  WebView Utils ////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////

	public static void loadLocalUrl(WebView webView, String urlBasePath, String htmlContent,
			boolean zoom) {
		webView.getSettings().setSupportZoom(zoom);
		webView.loadDataWithBaseURL(urlBasePath, htmlContent, "text/html", "utf-8", null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			try {
				final Method method = View.class.getMethod("setLayerType", int.class, Paint.class);
				method.invoke(webView, 1, new Paint());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// ////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////// Global Keyboard Hide / Show Control /////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Show Virtual Keyboard by force
	 */
	public static void showVirturalKeyboard(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(InputType.TYPE_CLASS_NUMBER,
				InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * Hide Virtual Keyboard by force
	 */
	public static boolean hideVirturalKeyboard(EditText editText) {
		InputMethodManager hideInputMethodManager = (InputMethodManager) editText.getContext()
				.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		hideInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		return true;
	}

	public static Bitmap toBitmap(byte[] bytes) {
        Bitmap bitmap = null;
        ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			bitmap = BitmapFactory.decodeStream(bis);
		}
        catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

	public static View getChildViewByPosition(int position, AdapterView<?> adapterView) {
	    final int firstListItemPosition = adapterView.getFirstVisiblePosition();
	    final int lastListItemPosition = firstListItemPosition + adapterView.getChildCount() - 1;
	
	    if (position < firstListItemPosition || position > lastListItemPosition ) {
	        return adapterView.getAdapter().getView(position, null, adapterView);
	    } else {
	        final int childIndex = position - firstListItemPosition;
	        return adapterView.getChildAt(childIndex);
	    }
	}
}
