package com.talha.app.application.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.util.concurrent.ThreadLocalRandom

@Module
@InstallIn(ActivityComponent::class)
object ThreadLocalRandomGeneratorModule {
    @Provides
    fun getThreadLocalRandom() = ThreadLocalRandom.current()
}