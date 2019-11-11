package com.liang.roomhelper

import com.liang.room.databaseBuilder

object DataBaseManager {

    val appDatabase: AppDatabase by lazy {
        appContext.databaseBuilder(
            AppDatabase::class.java,
            AppDatabase::class.java.simpleName
        ).allowMainThreadQueries().build()
    }

}

