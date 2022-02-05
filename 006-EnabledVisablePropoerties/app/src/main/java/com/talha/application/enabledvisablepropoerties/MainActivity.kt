package com.talha.application.enabledvisablepropoerties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private lateinit var mLinearLayout: LinearLayout
    private lateinit var mLinearLayoutPersonInfo: LinearLayout
    // private val visibilities = arrayOf(View.)

    private fun clearPersonInfo(){
        mLinearLayoutPersonInfo.children.filter { it !is Button && it is TextView}
                .map { it as TextView }
                .forEach { it.text ="" }
    }

    private fun initialize() {
        initPersonInfoLayout()
    }

    private fun initPersonInfoLayout() {
        mLinearLayoutPersonInfo = findViewById(R.id.mainActivityLinearLayoutPersonInfo)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    fun onPersonTextViewClicked(view: View) {}
    fun onOKButtonClicked(view: View) {}
    fun onEnableDisableButtonClicked(view: View) {
        if(mLinearLayoutPersonInfo.visibility != View.VISIBLE){
           return
        }

        mLinearLayoutPersonInfo.children.filter { it is EditText || it is Button }
                .forEach { it.isEnabled = !it.isEnabled }

    }

    fun onVisableInvisableButtonClicked(view: View) {
        mLinearLayoutPersonInfo.visibility = Visibility.visibility
    }

    fun onClearButtonClicked(view: View) {
        clearPersonInfo()
    }
}