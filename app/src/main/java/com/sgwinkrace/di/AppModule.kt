package com.sgwinkrace.di

import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.datastore.PreferenceStorage
import com.sgwinkrace.network.ApiService
import com.sgwinkrace.utils.NetworkUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideBaseURL(): String = NetworkUtils.BASE_URL

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okhttpClient = OkHttpClient.Builder()

        okhttpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder().addQueryParameter("APP_KEY", "SpTka6TwrTsXl28P").build()
            request.url(url)
            val response = chain.proceed(request.build())
            return@addInterceptor response
        }
        okhttpClient.addInterceptor(loggingInterceptor)
        okhttpClient.callTimeout(60, TimeUnit.SECONDS)
        okhttpClient.connectTimeout(60, TimeUnit.SECONDS)
        okhttpClient.writeTimeout(60, TimeUnit.SECONDS)
        okhttpClient.readTimeout(60, TimeUnit.SECONDS)
        val okhtttp = okhttpClient.build()
        return okhtttp

    }

    @Provides
    fun provideConvertorFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        con: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(con)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }





}