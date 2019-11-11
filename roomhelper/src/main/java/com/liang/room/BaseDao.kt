package com.liang.room

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BaseDao<T> {
    fun queryAll(): DataSource.Factory<Int, T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<T>)

    @Delete
    fun delete(data: T)

    @Delete
    fun delete(data: List<T>)

    @Update
    fun update(data: T)

    @Update
    fun update(data: List<T>)

}