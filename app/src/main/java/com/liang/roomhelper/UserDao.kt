package com.liang.roomhelper

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.liang.room.BaseDao

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user")
    override fun queryAll(): DataSource.Factory<Int, UserEntity>

    //自定义查询
    @Query("select * from user where uid = :uid")
    fun loadUser(uid: Int): LiveData<UserEntity>

    @Query("DELETE FROM user")
    fun clearTable()
}