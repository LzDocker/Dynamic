package com.docker.core.di.module.cache;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface CacheEntityDao {

    @Query("SELECT * FROM CacheEntity WHERE cachekey = :key")
    LiveData<CacheEntity> LoadCache(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertCache(CacheEntity... cacheEntities);

    @Query("SELECT * FROM CacheEntity WHERE cachekey = :key")
    CacheEntity LoadCacheSync(String key);

}
