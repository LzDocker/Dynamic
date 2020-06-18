package com.docker.core.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel implements LifecycleObserver {

    public BaseViewModel() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void any() {

    }

}
