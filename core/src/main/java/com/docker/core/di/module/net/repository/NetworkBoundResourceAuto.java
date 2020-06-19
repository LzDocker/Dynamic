package com.docker.core.di.module.net.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


import com.docker.core.di.module.cache.CacheDatabase;
import com.docker.core.di.module.cache.CacheEntity;
import com.docker.core.di.module.cache.CacheStrategy;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;
import com.docker.core.utils.AppExecutors;
import com.docker.core.utils.IOUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangxindang on 2018/12/25.
 */
public abstract class NetworkBoundResourceAuto<ResultType> {
    private AppExecutors appExecutors;
    private CacheStrategy cacheStrategy;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private String cachekey;
    private CacheDatabase cacheDatabase;

    /*
     * 需要缓存的构造
     * */
    @MainThread
    public NetworkBoundResourceAuto(AppExecutors appExecutors, CacheStrategy cacheStrategy, CacheDatabase cacheDatabase, String cachekey) {
        this.appExecutors = appExecutors;
        this.cacheStrategy = cacheStrategy;
        this.cacheDatabase = cacheDatabase;
        this.cachekey = cachekey;
        startFetch();
    }

    /*
     * 不需要缓存的构造
     * */
    @MainThread
    public NetworkBoundResourceAuto() {
        this.cacheStrategy = CacheStrategy.NO_CACHE;
        NO_ChCHE();
    }

    @MainThread
    public NetworkBoundResourceAuto(int flag) {
        SpecLoad();
    }

    /*
     * 特殊情况 数据格式不统一
     *
     * */

    private void SpecLoad() {
        setZoneValue(Resource.loading(null, null));
        LiveData<ApiResponse<ResultType>> apiResponse = createSpecCall();
        result.addSource(apiResponse, response -> {
            if (response != null && response.isSuccessful() && response.call != null) {
                result.setValue(Resource.loading(response.call));
            } else {
                result.removeSource(apiResponse);
                if (response != null && response.isSuccessful() && response.body != null) {
                    setZoneValue(Resource.success((ResultType) response.body));
                } else {
                    onFetchFailed();
                    result.addSource(apiResponse,
                            newData -> {
                                result.removeSource(apiResponse);
                                setZoneValue(Resource.error(response.errorMessage, null));
                            });
                }
            }
        });
    }


    /*
     * 回调 ---》
     *
     * ===> loading ===  bussiness error / networkerror
     *
     * ===>  loading === success
     * */
    private void NO_ChCHE() {
        setZoneValue(Resource.loading(null, null));
        LiveData<ApiResponse<BaseResponse<ResultType>>> apiResponse = createCall();
        result.addSource(apiResponse, (ApiResponse<BaseResponse<ResultType>> response) -> {
            if (response.isSuccessful() && response.call != null) {
                result.setValue(Resource.loading(response.call));
            } else {
                result.removeSource(apiResponse);
                if (response.isSuccessful() && response.body != null) {
                    if (response != null && response.body.getErrno() != null && Integer.parseInt(response.body.getErrno()) != 0) { // bussiness error
                        result.addSource(apiResponse,
                                newData -> {
                                    result.removeSource(apiResponse);
                                    setZoneValue(Resource.bussinessError(response.body.getErrmsg(), response.body.getRst()));
                                });
                    } else {
                        setZoneValue(Resource.success(response.body.getRst(), response.body.getErrmsg()));
                    }
                } else {
                    onFetchFailed();
                    result.addSource(apiResponse,
                            newData -> {
                                result.removeSource(apiResponse);
                                setZoneValue(Resource.error(response.errorMessage, null));
                            });
                }
            }

        });

    }
    /*
    FIRST_CACHE_THEN_REQUEST
     * 回调 ---》 第二个loading带着缓存数据，但缓存不保证不为空
     *
     * ===> loading === loading====> bussiness error / networkerror
     *
     * ===>  loading ===loading ===> success
     * */


    /* IF_NONE_CACHE_REQUEST
     * 回调 ---》 有缓存直接返回，没有就请求网络 success 中包含的数据就是可用数据
     *
     * ===> loading ====> bussiness error / networkerror
     *
     * ===>  loading  ===> success
     * */

    /*
    REQUEST_FAILED_READ_CACHE
     * 回调 ---》 网络成功 ---》 loading ---- success
     *           网络失败 ---》 loading ----- loading(数据为空，包含网络出错原因) ---> success(可能是空的缓存)
     * */


    private void startFetch() {
        setZoneValue(Resource.loading(null, null));
        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                fetchFromdb();
                break;
            case FIRST_CACHE_THEN_REQUEST:
                fetchFromdb();
                break;
            case REQUEST_FAILED_READ_CACHE:
                fetchFromNetwork();
                break;
        }
    }

    private void fetchFromdb() {
        LiveData<ApiResponse<BaseResponse<ResultType>>> dbSource = loadFromDb();
        result.addSource(dbSource, newdata -> {
            result.removeSource(dbSource);
            if (newdata != null) {
                onFetchDbSuccess(newdata);
            } else {
                onFetchDbFiled();
            }
        });
    }

    private void onFetchDbFiled() {
        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                fetchFromNetwork();
                break;
            case FIRST_CACHE_THEN_REQUEST:
                setZoneValue(Resource.loading(null, null));
                fetchFromNetwork();
                break;
            case REQUEST_FAILED_READ_CACHE:
                setZoneValue(Resource.error("-1", null)); // 没有缓存
                break;
        }
    }

    private void onFetchDbSuccess(ApiResponse<BaseResponse<ResultType>> newdata) {

        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                break;
            case FIRST_CACHE_THEN_REQUEST:
                setZoneValue(Resource.loading(null, (ResultType) newdata.body.getRst()));
                fetchFromNetwork();
                break;
            case REQUEST_FAILED_READ_CACHE:
                setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                break;
        }

    }


    private void fetchFromNetwork() {
        LiveData<ApiResponse<BaseResponse<ResultType>>> apiResponse = createCall();
        result.addSource(apiResponse, (ApiResponse<BaseResponse<ResultType>> response) -> {
            if (response.isSuccessful() && response.call != null) {
                result.setValue(Resource.loading(response.call));
            } else {
                result.removeSource(apiResponse);
                if (response.isSuccessful() && response.body != null) {
                    if (response != null && response.body.getErrno() != null && Integer.parseInt(response.body.getErrno()) != 0) { // bussiness error
                        onFetchNetFailed(0, response);
//                        result.addSource(apiResponse,
//                                newData -> {
//                                    result.removeSource(apiResponse);
//                                    setZoneValue(Resource.bussinessError(response.body.getErrmsg(), response.body.getRst()));
//                                });
                    } else {
//                        setZoneValue(Resource.success(response.body.getRst(), response.body.getErrmsg()));
                        appExecutors.diskIO().execute(() -> {
                            saveCallResult(response);
                            appExecutors.mainThread().execute(() -> {
                                        LiveData<ApiResponse<BaseResponse<ResultType>>> dbSource1 = loadFromDb();
                                        result.addSource(dbSource1,
                                                newData -> {
                                                    result.removeSource(dbSource1);
                                                    onFetchNetSuccess(newData);
                                                });
                                    }
                            );
                        });
                    }
                } else {
                    onFetchFailed();
//                    result.addSource(apiResponse,
////                            newData -> {
////                                result.removeSource(apiResponse);
////                                setZoneValue(Resource.error(response.errorMessage, null));
////                            });

                    onFetchFailed();
                    onFetchNetFailed(1, response);
                }
            }

        });


//        LiveData<ApiResponse<BaseResponse<ResultType>>> apiResponse = createCall();
//        result.addSource(apiResponse, response -> {
//            result.removeSource(apiResponse);
//            if (response.isSuccessful()) {
//                if (response.body == null || Integer.parseInt(response.body.getErrno()) != 0) { // bussiness error
//                    onFetchNetFailed(0, response);
//                } else {
//                    appExecutors.diskIO().execute(() -> {
//                        saveCallResult(response);
//                        appExecutors.mainThread().execute(() -> {
//                                    LiveData<ApiResponse<BaseResponse<ResultType>>> dbSource1 = loadFromDb();
//                                    result.addSource(dbSource1,
//                                            newData -> {
//                                                result.removeSource(dbSource1);
//                                                onFetchNetSuccess(newData);
//                                            });
//                                }
//                        );
//                    });
//                }
//            } else {
//                onFetchFailed();
//                onFetchNetFailed(1, response);
//            }
//        });
    }


    /*
     * 1  error
     * 0  bussiness error
     * */
    private void onFetchNetFailed(int errType, ApiResponse<BaseResponse<ResultType>> newdata) {

        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                if (errType == 1) {
                    setZoneValue(Resource.error(newdata.errorMessage, null));
                } else {
                    setZoneValue(Resource.bussinessError(newdata.body == null ? "" : newdata.body.getErrmsg(), null));
                }
                break;
            case FIRST_CACHE_THEN_REQUEST:
                if (errType == 1) {
                    setZoneValue(Resource.error(newdata.errorMessage, null));
                } else {
                    setZoneValue(Resource.bussinessError(newdata.body.getErrmsg(), null));
                }

                break;
            case REQUEST_FAILED_READ_CACHE:
                if (errType == 1) {
                    setZoneValue(Resource.loading(newdata.errorMessage, null));
                } else {
                    setZoneValue(Resource.loading(newdata.body.getErrmsg(), null));
                }
                fetchFromdb();
                break;
        }

    }


    private void onFetchNetSuccess(ApiResponse<BaseResponse<ResultType>> newdata) {

        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                if (newdata != null) {
                    setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                } else {
                    setZoneValue(Resource.success(null));
                }
                break;
            case FIRST_CACHE_THEN_REQUEST:
                if (newdata != null) {
                    setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                } else {
                    setZoneValue(Resource.success(null));
                }
                break;
            case REQUEST_FAILED_READ_CACHE:
                if (newdata != null) {
                    setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                } else {
                    setZoneValue(Resource.success(null));
                }
                fetchFromdb();
                break;
        }
    }


    @MainThread
    private void setZoneValue(Resource<ResultType> newValue) {
//        Timber.e("========" + newValue.status);
        result.setValue(newValue);
    }

    protected void onFetchFailed() {
    }

    public MediatorLiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    private void saveCallResult(@NonNull ApiResponse<BaseResponse<ResultType>> response) {
        appExecutors.diskIO().execute(() -> {
            Gson gson = new Gson();
            CacheEntity cacheEntity = new CacheEntity();
            cacheEntity.setKey(cachekey);
            cacheEntity.setData(IOUtils.toByteArray(response));
            cacheEntity.setGsonData(gson.toJson(response));
            cacheDatabase.cacheEntityDao().insertCache(cacheEntity);
        });

    }

    @NonNull
    @MainThread
    private LiveData<ApiResponse<BaseResponse<ResultType>>> loadFromDb() {
        final MediatorLiveData<ApiResponse<BaseResponse<ResultType>>> responseMediatorLiveData = new MediatorLiveData<>();
        LiveData<CacheEntity> souce = cacheDatabase.cacheEntityDao().LoadCache(cachekey);
        responseMediatorLiveData.addSource(souce, newdata -> {
            responseMediatorLiveData.removeSource(souce);
            if (newdata != null && newdata.getData() != null) {
//                Gson gson = new Gson();
//                responseMediatorLiveData.setValue(gson.fromJson(newdata.getGsonData(), new TypeToken<ApiResponse<BaseResponse<Object>>>() {
//                }.getType()));
                responseMediatorLiveData.setValue((ApiResponse<BaseResponse<ResultType>>) IOUtils.toObject(newdata.getData()));
            } else {
                responseMediatorLiveData.setValue(null);
            }
        });
        return responseMediatorLiveData;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<BaseResponse<ResultType>>> createCall();

    @NonNull
    @MainThread
    protected LiveData<ApiResponse<ResultType>> createSpecCall() {
        return null;
    }
}

