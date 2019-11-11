package com.liang.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

inline fun <reified T : RoomDatabase> Context.databaseBuilder(
    clazz: Class<T>,
    dbName: String
): RoomDatabase.Builder<T> {
    return Room.databaseBuilder(this.applicationContext, clazz, dbName)
}

