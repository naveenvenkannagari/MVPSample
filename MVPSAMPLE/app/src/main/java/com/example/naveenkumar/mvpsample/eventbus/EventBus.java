package com.example.naveenkumar.mvpsample.eventbus;

import com.example.naveenkumar.mvpsample.domain.BMSDataObject;

import java.util.ArrayList;
import java.util.WeakHashMap;


public class EventBus implements IEventBus {

    private WeakHashMap<Class<?extends BMSDataObject>, ArrayList<IEventSubscriber>> mEventSubscriberListLookUp;
    private static EventBus sEventBus;

    private EventBus() {
        mEventSubscriberListLookUp = new WeakHashMap<Class<? extends BMSDataObject> , ArrayList<IEventSubscriber>>();
    }

    public static synchronized EventBus getInstance() {
        if (sEventBus == null) {
            sEventBus = new EventBus();
        }
        return sEventBus;
    }

    @Override
    public void Subscribe(Class<? extends BMSDataObject> dataObjectClass,
                          IEventSubscriber eventSubscriber) {
        if (mEventSubscriberListLookUp.containsKey(dataObjectClass)) {
            ArrayList<IEventSubscriber> eventSubscriberList = mEventSubscriberListLookUp.get(dataObjectClass);
            eventSubscriberList.add(eventSubscriber);
        } else {
            ArrayList<IEventSubscriber> eventSubscriberList = new ArrayList<IEventSubscriber>();
            eventSubscriberList.add(eventSubscriber);
            mEventSubscriberListLookUp.put(dataObjectClass, eventSubscriberList);
        }
    }

    @Override
    public void UnSubscribe(Class<? extends BMSDataObject> dataObjectClass, IEventSubscriber eventSubscriber) {
        if (mEventSubscriberListLookUp.containsKey(dataObjectClass)) {
            ArrayList<IEventSubscriber> eventSubscriberList = mEventSubscriberListLookUp.get(dataObjectClass);
            eventSubscriberList.remove(eventSubscriber);
            if (eventSubscriberList.size() == 0) {
                mEventSubscriberListLookUp.remove(dataObjectClass);
            }
        }
    }

    @Override
    public void Publish(BMSDataObject domainEvent) {
        if (domainEvent == null) {
            return;
        }

        Class<? extends BMSDataObject> domainEventClass = domainEvent.getClass();
        if (mEventSubscriberListLookUp.containsKey(domainEventClass)) {
            ArrayList<IEventSubscriber> eventSubscriberList = mEventSubscriberListLookUp.get(domainEventClass);
            for (int i = 0; i < eventSubscriberList.size(); i++) {
                IEventSubscriber eventSubscriber = (IEventSubscriber) eventSubscriberList.get(i);
                eventSubscriber.OnEvent(domainEvent);
            }
        }
    }
}
