package com.docker.core.di.module.net.repository;

import androidx.annotation.Nullable;


public class NitNetBoundObserver<T> implements androidx.lifecycle.Observer<Resource<T>> {


    private NitBoundCallback<T> nitBoundCallback;

    public NitNetBoundObserver(NitBoundCallback<T> netBoundCallback) {
        this.nitBoundCallback = netBoundCallback;
    }

    @Override
    public void onChanged(@Nullable Resource<T> tResource) {
        switch (tResource.status) {
            case LOADING:
                if (tResource.data != null) {
                    nitBoundCallback.onCacheComplete(tResource.data);
                } else {
                    if (tResource.call != null) {
                        nitBoundCallback.onLoading(tResource.call);
                    } else {
                        nitBoundCallback.onLoading();
                    }
                }
                break;
            case SUCCESS:
                nitBoundCallback.onComplete(tResource);
                nitBoundCallback.onComplete();
                break;
            case BUSSINESSERROR:
                nitBoundCallback.onBusinessError(tResource);
                nitBoundCallback.onComplete();
                break;
            case ERROR:
                nitBoundCallback.onNetworkError(tResource);
                nitBoundCallback.onComplete();
                break;
        }
    }
}
