package com.liang.roomhelper

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.paging.DataSource
import androidx.room.RoomDatabase
import com.liang.room.DatabaseViewModel
import java.util.ArrayList

class UserViewModel(application: Application) :
    DatabaseViewModel<UserEntity, UserDao>(application) {

    override fun getRoomDatabase(): RoomDatabase {
        return DataBaseManager.appDatabase
    }

    override fun getDao(): UserDao {
        return DataBaseManager.appDatabase.getUserDao()
    }

    override fun queryAll(): DataSource.Factory<Int, UserEntity> {
        return bindAllData()
    }

    //数据库没有查询到数据
    override fun onZeroItemsLoaded() {
        Log.e("onZeroItemsLoaded","去网络拉取数据。。。")
        getDataWithNetwork()
    }

    //用户已到达列表末尾,加载网络数据
    override fun onItemAtEndLoaded(itemAtEnd: UserEntity) {
        Log.e("onItemAtEndLoaded","去网络拉取数据。。。")
        getDataWithNetwork()
    }

    //模拟网络加载
    var id = 0

    var d = false
    private fun getDataWithNetwork() {
        if (d){
            return
        }
        d = true
        Toast.makeText(getApplication(), "去网络拉取数据。。。", Toast.LENGTH_SHORT).show()

        var index = 0
        val users = ArrayList<UserEntity>()
        while (index < 50) {
            index++
            id++
            val user = UserEntity(id, "用户: $index", index)
            users.add(user)
        }
        insert(users)
        d = false
    }

}