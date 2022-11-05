package com.iitms.bfr.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iitms.bfr.data.db.dao.*
import com.iitms.bfr.data.model.UserInfo

@Database(
    entities = [UserInfo::class],
    version = 1,
    exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun userInfo(): UserInfoDao
}