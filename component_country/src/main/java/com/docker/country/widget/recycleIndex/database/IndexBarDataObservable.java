package com.docker.country.widget.recycleIndex.database;

import android.database.Observable;

/**
 * Created by Sun on 16/10/13.
 */
public class IndexBarDataObservable extends Observable<IndexBarDataObserver> {

    public void notifyChanged() {
        synchronized (mObservers) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

}
