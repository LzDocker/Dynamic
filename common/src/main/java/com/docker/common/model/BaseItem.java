package com.docker.common.model;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

public  abstract class BaseItem extends BaseObservable implements BaseItemModel, Serializable {

    public String sampleName = getClass().getSimpleName();

    public int index;



}
