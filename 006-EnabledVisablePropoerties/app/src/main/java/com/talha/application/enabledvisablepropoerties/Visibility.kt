package com.talha.application.enabledvisablepropoerties

import android.view.View

object Visibility {
    private val mVisibilities = arrayOf(View.VISIBLE, View.INVISIBLE, View.GONE)
    private var mIndex = 0
    val visibility: Int
        get() {
            mIndex %= mVisibilities.size
            return mVisibilities[mIndex++]
        }

}