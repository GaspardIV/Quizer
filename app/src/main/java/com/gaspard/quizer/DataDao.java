package com.gaspard.quizer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM DataEntity")
    List<DataEntity> getAll();

    @Insert
    void insertAll(DataEntity... dataEntities);
}