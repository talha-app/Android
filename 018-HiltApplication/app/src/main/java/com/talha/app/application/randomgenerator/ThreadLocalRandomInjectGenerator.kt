package com.talha.app.application.randomgenerator

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject


class ThreadLocalRandomInjectGenerator @Inject constructor(
    val threadLocalRandom: ThreadLocalRandom,
    @ActivityContext val mContext: Context
) {
    init {
        Toast.makeText(mContext, "ThreadLocalRandomInjectGenerator", Toast.LENGTH_LONG).show()
    }

    fun generateRandomNumber(min: Int, max: Int) = threadLocalRandom.nextInt(min, max)

}
