package com.talha.app.application.randomgenerator

import javax.inject.Inject

class RandomGeneratorHelper @Inject constructor(
    val randomGenerator: RandomGenerator,
    val threadLocalRandomGenerator: ThreadLocalRandomGenerator,
    val threadLocalRandomInjectGenerator: ThreadLocalRandomInjectGenerator
) {
    fun getRandomNumber(min: Int, max: Int) = randomGenerator.generateRandomNumber(min, max)

    fun getThreadLocalRandomNumber(min: Int, max: Int) =
        threadLocalRandomGenerator.generateRandomNumber(min, max)

    fun getThreadLocalInjectRandomNumber(min: Int, max: Int) =
        threadLocalRandomInjectGenerator.generateRandomNumber(min, max)

}