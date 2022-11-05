package com.iitms.bfr.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.iitms.bfr.data.db.MyDataBase
import com.iitms.bfr.data.db.dao.UserInfoDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module class RoomDbModule {

    @Singleton
    @Provides
    fun provideMyDataBase(context: Context, @Named("databaseKey")databaseKey : String): MyDataBase {


        return Room.databaseBuilder(
            context,
            MyDataBase::class.java!!, "digital.db"
        )
            .fallbackToDestructiveMigration()
            //.addMigrations(MIGRATION_1_2)
            //.openHelperFactory(SupportFactory(databaseKey.toByteArray()))
            .allowMainThreadQueries()
            .build()


    }

    @Singleton
    @Provides
    fun provideUserInfoDao(myDataBase: MyDataBase): UserInfoDao {
        return myDataBase.userInfo()
    }

}