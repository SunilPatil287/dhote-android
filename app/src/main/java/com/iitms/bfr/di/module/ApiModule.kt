package com.iitms.bfr.di.module

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iitms.bfr.BuildConfig
import com.iitms.bfr.data.ApiClient
import com.iitms.bfr.data.AuthorizationInterceptor

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideInterceptor(sharedPreferences: SharedPreferences): Interceptor {
        return AuthorizationInterceptor(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, interceptorHeader: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        client.readTimeout(60, TimeUnit.SECONDS)
        client.connectTimeout(30, TimeUnit.SECONDS)

        client.addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            builder.method(original.method, original.body)
            chain.proceed(builder.build())
        }
        client.addInterceptor(interceptorHeader)
        if (BuildConfig.DEBUG) {
            client.addInterceptor(interceptor)
        }
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String, gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

}
