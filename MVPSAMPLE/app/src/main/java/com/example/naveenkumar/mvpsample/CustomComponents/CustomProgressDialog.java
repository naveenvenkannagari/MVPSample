package com.example.naveenkumar.mvpsample.CustomComponents;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.naveenkumar.mvpsample.R;



public class CustomProgressDialog extends Dialog {

    private ImageView mRotatingImageView;

    public CustomProgressDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        setTitle(null);
        setCanceledOnTouchOutside(false);
        //setCancelable(false);
        //setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mRotatingImageView = new ImageView(context);
        mRotatingImageView.setImageResource(R.drawable.spinner);
        layout.addView(mRotatingImageView, params);
        addContentView(layout, params);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(3000);
        mRotatingImageView.setAnimation(anim);
        mRotatingImageView.startAnimation(anim);
    }
}
