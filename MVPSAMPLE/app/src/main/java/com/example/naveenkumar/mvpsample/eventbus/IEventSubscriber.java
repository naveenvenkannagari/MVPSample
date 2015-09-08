package com.example.naveenkumar.mvpsample.eventbus;

import com.example.naveenkumar.mvpsample.domain.BMSDataObject;

public interface IEventSubscriber{

     void OnEvent(BMSDataObject event);
}
