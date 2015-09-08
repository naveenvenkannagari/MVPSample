package com.example.naveenkumar.mvpsample.eventbus;


import com.example.naveenkumar.mvpsample.domain.BMSDataObject;

public interface IEventBus {

    void Subscribe(Class<? extends BMSDataObject> bmsDataObjectType, IEventSubscriber eventSubscriber);

    void UnSubscribe(Class<? extends BMSDataObject> bmsDataObjectType, IEventSubscriber eventSubscriber);

    void Publish(BMSDataObject domainEvent);
}
