package com.example.techtest.domain.di

import android.content.Context
import com.example.techtest.BuildConfig
import com.example.techtest.domain.locale.AppDatabase
import com.example.techtest.domain.remote.HeaderInterceptor
import com.example.techtest.domain.remote.ProfileRemoteDataSource
import com.example.techtest.domain.remote.ProfileService
import com.example.techtest.domain.repository.ProfileRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideUserService(retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userService: ProfileService) =
        ProfileRemoteDataSource(userService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ProfileRemoteDataSource,
        localDataSource: AppDatabase
    ) =
        ProfileRepository(remoteDataSource, localDataSource)
}