package com.talha.app.application

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.talha.aar.application.R
import com.talha.aar.application.databinding.ActivityMainBinding
import com.talha.android.util.application.App
import com.talha.app.application.randomgenerator.RandomGeneratorHelper
import com.talha.app.application.viewmodel.RandomInfo
import com.talha.app.application.viewmodel.ResultInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var randomHelper: RandomGeneratorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun onDisplayNumberMenuSelected() {
        var resultInfo = mBinding.randomResultInfo
        var localResultInfo = mBinding.threalLocalRandomResultInfo
        var randomInfo = mBinding.randomInfo
        val random1 = randomHelper.getRandomNumber(randomInfo!!.min, randomInfo.max)
        val random2 = randomHelper.getThreadLocalInjectRandomNumber(randomInfo.min, randomInfo.max)
        resultInfo?.result = random1
        localResultInfo?.result = random2
        mBinding.invalidateAll()

    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.randomInfo = RandomInfo()
        mBinding.randomResultInfo = ResultInfo()
        mBinding.threalLocalRandomResultInfo = ResultInfo()
    }

    private fun initialize() {
        initBinding()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainActivityMenuItemDisplay -> Toast.makeText(
                App.getInstance(),
                mBinding.randomInfo.toString(),
                Toast.LENGTH_SHORT
            ).show()
            R.id.mainActivityMenuItemOpenAboutActivity -> onDisplayNumberMenuSelected()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onDisplayButtonClicked(view: View) {
        onDisplayNumberMenuSelected()
    }

}