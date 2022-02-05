package com.talha.sample.samplecounterwithexecutors.model

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors

@Module
@InstallIn(SingletonComponent::class)
object ThreadPoolModule {
    @Provides
    fun provideThreadPool() = Executors.newCachedThreadPool()
}