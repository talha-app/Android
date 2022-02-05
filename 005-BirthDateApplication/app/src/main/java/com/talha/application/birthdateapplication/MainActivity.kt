package com.talha.application.birthdateapplication

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.talha.application.birthdateapplication.global.Person
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextBirthDate: EditText
    private lateinit var mEditTextName: EditText
    private lateinit var mTextViewAge: TextView
    private lateinit var mTextViewPerson: TextView
    private lateinit var name: String
    private val mFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private fun initViews() {
        mEditTextBirthDate = findViewById(R.id.mainActivityEditTextBirthDate)
        mEditTextName = findViewById(R.id.mainActivityEditTextName)
        mTextViewPerson = findViewById(R.id.mainActivityTextViewPerson)
        mTextViewAge = findViewById(R.id.mainActivityTextViewAge)
    }

    private fun initialize() {
        initViews()
        mEditTextName.addTextChangedListener { text -> name = text.toString() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    fun onOKButtonClicked(view: View) {
        getPerson().also {
            mTextViewPerson.tag = it
            mTextViewPerson.text = it.name
        }
    }

    private fun getPerson(): Person {
        var birthDate: LocalDate? = null
        try {
            birthDate = LocalDate.parse(mEditTextBirthDate.text.toString(), mFormatter)
            val now = LocalDate.now()
            val birthDay = birthDate.withYear(now.year)
            val age = ChronoUnit.DAYS.between(birthDate, now) / 365.0

            if (age < 0)
                throw DateTimeParseException("", "", 0);

            val message = when {
                now.isAfter(birthDay) -> "Geçmiş doğum gününüz kutlu olsun"
                now.isBefore(birthDay) -> "Doğum gününüzü şimdiden kutlu olsun"
                else -> "Doğum gününüz kutlu olsun"
            }

            mTextViewAge.setText("Yeni yaşınız:%s".format(age))
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } catch (ex: DateTimeParseException) {
            Toast.makeText(this, "Geçersiz tarih formatı", Toast.LENGTH_LONG).show()
        }
        return Person(name, birthDate)
    }

    fun onExitButtonClicked(view: View) {

        if (mTextViewAge.text.equals("TextView")) {
            var dialog = AlertDialog.Builder(this)
                .setMessage("İşlemi yapmadınız çıkmak istediğinize emin misiniz?")
                .setNegativeButton("Hayır") { _, _ -> }
                .setPositiveButton("Evet") { _, _ -> this.finish() }
                .setNeutralButton("iptal") { _, _ -> }
                .create()
            dialog.show()
        } else {
            finish()
        }
    }

    fun onTextViewPersonClicked(view: View) {
        val person = mTextViewPerson.tag as Person
       val dlg = AlertDialog.Builder(this)
            .setMessage(person.toString())
            .setNeutralButton(
                "Kapat"
            ) { _, _ ->
                //
            }.create()
        dlg.show()
    }
}