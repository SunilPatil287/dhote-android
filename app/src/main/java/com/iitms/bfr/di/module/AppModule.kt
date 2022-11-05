package com.iitms.bfr.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import android.os.Handler
import javax.inject.Singleton

@Module class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    fun provideHandler(): Handler {
        return Handler()
    }

}