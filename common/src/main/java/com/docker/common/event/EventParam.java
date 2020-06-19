package com.docker.common.event;
import java.io.Serializable;
public class EventParam<T> implements Serializable {

    /*
    *
    *
    * */
    public String mPageID;


    public String mBlockID;


    public String mCardID;


    public T mData;


    public EventParam(T mData) {
        this.mData = mData;
    }
}
