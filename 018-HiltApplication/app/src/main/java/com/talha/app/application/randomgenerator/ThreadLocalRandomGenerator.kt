package com.talha.app.application.randomgenerator

import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject
import kotlin.random.Random

class ThreadLocalRandomGenerator @Inject constructor() {
    private val mThreadLocalRandom = ThreadLocalRandom.current()
    fun generateRandomNumber(min: Int, max: Int) = mThreadLocalRandom.nextInt(min, max)

}