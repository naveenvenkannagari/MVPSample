package com.example.naveenkumar.mvpsample.eventbus.events;

import com.example.naveenkumar.mvpsample.domain.BMSDataObject;


public class NetResponseProcessingCompletedEvent extends BMSDataObject {


	public static final String TAG = "NetResponseProcessingCompletedEvent";

	public String mRequestType;
	public String mContainedObjectUniqueId;
	public BMSDataObject mProcessedResultDTO;


}
