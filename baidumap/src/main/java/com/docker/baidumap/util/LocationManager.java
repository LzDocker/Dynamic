package com.docker.baidumap.util;

import android.Manifest;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.base.BaseActivity;
import com.docker.core.command.ReplyCommandParam;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocationManager implements LifecycleObserver {

    public LocationClient mLocationClient = null;
    public GeoCoder geocode = GeoCoder.newInstance();

    @Inject
    public LocationManager() {

    }

    private int locationCount = 0;

    public void processLocation(BaseActivity context,
                                ReplyCommandParam locCommand,
                                ReplyCommandParam geoCommand) {

        context.getLifecycle().addObserver(this);
        locationCount = 0;
        mLocationClient = new LocationClient(context);
        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            option.setOpenGps(true); // 打开gps
                            option.setCoorType("bd09ll"); // 设置坐标类型
                            option.setScanSpan(1000);
                            mLocationClient.setLocOption(option);
                            mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                                @Override
                                public void onReceiveLocation(BDLocation location) {
                                    if (location.getCountry() != null) {
                                        mLocationClient.stop();
                                        locCommand.exectue(location);
                                        processGeoCode(location, geoCommand);
                                    } else {
                                        locationCount++;
                                        if (locationCount > 1) {
                                            mLocationClient.stop();
                                            locCommand.exectue(null);
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            locCommand.exectue(null);
                        }
                    }
                });
    }


    protected void processGeoCode(BDLocation location, ReplyCommandParam geoCommand) {
        //新建编码查询对象
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            /**
             * 反地理编码查询结果回调函数
             * @param result  反地理编码查询结果
             */
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    //得到位置
                    geoCommand.exectue(result);
//                    result.getAddressDetail().countryCode
                } else {
                    geoCommand.exectue(null);
                }
            }

            /**
             * 地理编码查询结果回调函数
             * @param result  地理编码查询结果
             */
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

            }
        };
        //设置查询结果监听者
        geocode.setOnGetGeoCodeResultListener(listener);
        //新建查询对象要查询的条件
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(latLng);
        // 发起反地理编码请求
        geocode.reverseGeoCode(options);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ON_DESTROY() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if (geocode != null) {
            geocode.destroy();
        }
    }

}
