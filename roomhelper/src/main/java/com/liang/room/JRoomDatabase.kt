package com.liang.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

inline fun RoomDatabase.clearTable(tabName: String) {
    assertNotMainThread()
    runInTransaction {
        openHelper.writableDatabase.execSQL("DELETE FROM $tabName")
    }
//    try {
//        beginTransaction()
//        _db.execSQL("DELETE FROM $table")
//        setTransactionSuccessful()
//    } finally {
//        endTransaction()
//        _db.query("PRAGMA wal_checkpoint(FULL)").close()
//        if (!_db.inTransaction()) {
//            _db.execSQL("VACUUM")
//        }
//    }
}


inline fun <reified T : RoomDatabase> Context.databaseBuilder(
    clazz: Class<T>,
    dbName: String
): RoomDatabase.Builder<T> {
    return Room.databaseBuilder(this.applicationContext, clazz, dbName)
}

