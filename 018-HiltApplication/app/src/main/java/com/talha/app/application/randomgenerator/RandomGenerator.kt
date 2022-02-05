package com.talha.app.application.randomgenerator

import javax.inject.Inject
import kotlin.random.Random

class RandomGenerator @Inject constructor() {
    fun generateRandomNumber(min: Int, max: Int) = Random.nextInt(min, max)

}