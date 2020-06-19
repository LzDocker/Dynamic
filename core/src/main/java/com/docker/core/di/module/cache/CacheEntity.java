package com.docker.core.di.module.cache;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CacheEntity implements Serializable {

//    @PrimaryKey(autoGenerate = true)
//    private long cid;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "cachekey")
    private String key;


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] data;


    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    private String gsonData;

    public String getGsonData() {
        return gsonData;
    }

    public void setGsonData(String gsonData) {
        this.gsonData = gsonData;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
