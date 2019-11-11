package com.liang.roomhelper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(@PrimaryKey val uid: Int, var name: String?, var age: Int)