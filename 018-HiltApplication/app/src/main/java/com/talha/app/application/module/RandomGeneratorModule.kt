package com.talha.app.application.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlin.random.Random

@Module
@InstallIn(ActivityComponent::class)
object RandomGeneratorModule {
    @Provides
    fun getRandomWithSeed(): Random {
        return Random(100)
    }
}