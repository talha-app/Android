package com.talha.application.a011_radiobuttonsampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private lateinit var mRadioGroupEducation: RadioGroup
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mTextViewResult: TextView

    private fun initialize() {
        initViews()
    }

    private fun initViews() {
        initEducationRadioGroup()
        initMaritalStatusRadioGroup()
        mTextViewResult = findViewById(R.id.mainActivityTextViewResult)
    }

    private fun initEducationRadioGroup() {
        mRadioGroupEducation = findViewById(R.id.mainActivityRadioGroupEducationStaus)
        mRadioGroupEducation.setOnCheckedChangeListener { rg, id ->

            val rb = findViewById<RadioButton>(id)
            if (rb != null)
                Toast.makeText(this, rb.text, Toast.LENGTH_LONG).show()

        }

    }

    private fun initMaritalStatusRadioGroup() {
        mRadioGroupMaritalStatus = findViewById(R.id.mainActivityRadioGroupMaritalStaus)
        mRadioGroupMaritalStatus.setOnCheckedChangeListener { _, id ->

            val rb = findViewById<RadioButton>(id)
            if (rb != null)
                Toast.makeText(this, rb.text, Toast.LENGTH_LONG).show()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    fun onSaveButtonClicked(view: View) {
        //   var linearLayout:LinearLayout = findViewById(R.id.mainActivityLinearLayout)
        // linearLayout.children.filter { it is RadioGroup || it is TextView }.map { it as RadioGroup }
        val selectedRadioButtonEducation =
            findViewById<RadioButton>(mRadioGroupEducation.checkedRadioButtonId)

        val selectedRadioButtonMarital =
            findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId)
        if (selectedRadioButtonEducation != null && selectedRadioButtonMarital != null) {
            "${selectedRadioButtonEducation.text} ${selectedRadioButtonMarital.text}".also {
                mTextViewResult.text = it
                mRadioGroupMaritalStatus.clearCheck()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateActivityMenuItemSend -> {
                Intent(this, UpdateActivity::class.java).also {
                    it.putExtra("object", mTextViewResult.text)
                    startActivityForResult(it, 56)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 56 && resultCode == 1234) {
            AlertDialog.Builder(this)
                .setPositiveButton("tamam"){_,_->{}}
                .setMessage("İşlem başarılı").show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}