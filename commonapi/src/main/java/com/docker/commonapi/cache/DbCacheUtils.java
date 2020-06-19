package com.docker.commonapi.cache;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.docker.core.command.ReplyCommand;
import com.docker.core.di.module.cache.CacheDatabase;
import com.docker.core.di.module.cache.CacheEntity;
import com.docker.core.utils.AppExecutors;
import com.docker.core.utils.IOUtils;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbCacheUtils {

    @Inject
    public DbCacheUtils(AppExecutors appExecutors,CacheDatabase cacheDatabase) {
        this.appExecutors = appExecutors;
        this.cacheDatabase = cacheDatabase;
    }

    AppExecutors appExecutors;

    CacheDatabase cacheDatabase;



    /*
     *
     * 保存数据  一对一关系
     * */
    @MainThread
    public void save(@NonNull String cachekey, Object o, ReplyCommand replyCommand) {
        appExecutors.diskIO().execute(() -> {
            CacheEntity cacheEntity = new CacheEntity();
            cacheEntity.setKey(cachekey);
            cacheEntity.setData(IOUtils.toByteArray(o));
            cacheDatabase.cacheEntityDao().insertCache(cacheEntity);
            if (replyCommand != null) {
                appExecutors.mainThread().execute(() -> replyCommand.exectue());
            }
        });
    }

    /*
     *
     * 保存数据  一对多关系 外部处理去重逻辑
     *
     * cachekey  缓存主键
     *
     * Object   缓存对象  可以是List集合
     *
     * ReplyCommand 回调函数
     * */
    @MainThread
    public LiveData<DbChacheEntry> saveDatas(@NonNull String cachekey, Object o, ReplyCommand replyCommand) {
        final MediatorLiveData<DbChacheEntry> responseMediatorLiveData = new MediatorLiveData<>();
        LiveData<CacheEntity> souce = cacheDatabase.cacheEntityDao().LoadCache(cachekey);
        responseMediatorLiveData.addSource(souce, newdata -> {
            responseMediatorLiveData.removeSource(souce);
            appExecutors.diskIO().execute(() -> {
                CacheEntity cacheEntity = new CacheEntity();
                cacheEntity.setKey(cachekey);
                DbChacheEntry dbChacheEntry = responseMediatorLiveData.getValue();
                if (dbChacheEntry == null) {
                    dbChacheEntry = new DbChacheEntry();
                }
                if (o instanceof Collection) {
                    dbChacheEntry.data.addAll((Collection<?>) o);
                } else {
                    dbChacheEntry.data.add(o);
                }
                cacheEntity.setData(IOUtils.toByteArray(dbChacheEntry));
                cacheDatabase.cacheEntityDao().insertCache(cacheEntity);
                appExecutors.mainThread().execute(() -> {
                    if (replyCommand != null) {
                        replyCommand.exectue();
                    }
                    responseMediatorLiveData.setValue((DbChacheEntry) IOUtils.toObject(cacheEntity.getData()));
                });
            });
        });
        return responseMediatorLiveData;
    }

    /*
     * 获取缓存
     * */
    @NonNull
    @MainThread
    public LiveData<Object> loadFromDb(String cachekey) {
        final MediatorLiveData<Object> responseMediatorLiveData = new MediatorLiveData<>();
        LiveData<CacheEntity> souce = cacheDatabase.cacheEntityDao().LoadCache(cachekey);
        responseMediatorLiveData.addSource(souce, newdata -> {
            responseMediatorLiveData.removeSource(souce);
            if (newdata != null && (newdata).getData() != null) {
                responseMediatorLiveData.setValue(IOUtils.toObject((newdata).getData()));
            } else {
                responseMediatorLiveData.setValue(null);
            }
        });
        return responseMediatorLiveData;
    }

    /*
     * 清空缓存
     * */
    public void clearCache(String cachekey, ReplyCommand replyCommand) {
        appExecutors.diskIO().execute(() -> {
            CacheEntity cacheEntity = new CacheEntity();
            cacheEntity.setKey(cachekey);
            cacheEntity.setData(null);
            cacheDatabase.cacheEntityDao().insertCache(cacheEntity);
            if (replyCommand != null) {
                appExecutors.mainThread().execute(() -> replyCommand.exectue());
            }
        });
    }
}
