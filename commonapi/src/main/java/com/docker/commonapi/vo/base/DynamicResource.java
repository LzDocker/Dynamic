package com.docker.commonapi.vo.base;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

public class DynamicResource extends BaseObservable implements Serializable {

    private int t;
    private String img;
    private String url;
    private String sort;

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
