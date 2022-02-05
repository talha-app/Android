package com.talha.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Optional;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText m_EditTextName;
    private EditText m_EditTextSurname;
    private TextView m_textViewFullName;

    @Override
    public void onClick(View v) {
        doOnClicked("OK3");
    }

    private static class PersonInfo {
        String name;
        String surname;

        PersonInfo(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        String getFullName() {
            return Character.toUpperCase(name.charAt(0)) + name.substring(1) + " " + surname.toUpperCase();
        }
    }

    private void initOKButton2(){
        Button okButton2 = this.findViewById(R.id.mainActivityButtonOK2);
        okButton2.setOnClickListener(v -> doOnClicked("OK2"));
    }

    private void initOKButton3(){
        Button okButton3 = this.findViewById(R.id.mainActivityButtonOK3);
        okButton3.setOnClickListener(this);
    }

    private void doOnClicked(String message) {
        Optional<PersonInfo> personInfo = getPersonInfo();
        if (personInfo.isPresent()) {
            m_textViewFullName.setText(message+" "+personInfo.get().getFullName());
        } else
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }

    private Optional<PersonInfo> getPersonInfo() {
        String name = m_EditTextName.getText().toString();
        String surname = m_EditTextSurname.getText().toString();

        return name.trim().isEmpty() || surname.trim().isEmpty() ? Optional.empty() :
                Optional.of(new PersonInfo(name, surname));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        initEditTexts();
        initTextViews();
        initOKButton2();
        initOKButton3();
    }

    private void initTextViews() {
        m_textViewFullName = findViewById(R.id.mainActivityTextViewFullname);
    }

    private void initEditTexts() {
        m_EditTextName = findViewById(R.id.mainActivityEditTextName);
        m_EditTextSurname = findViewById(R.id.mainActivityEditTextSurname);
    }

    public void onFullnameTextViewClicked(View view) {
        Toast.makeText(this, m_textViewFullName.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void onOKButtonClicked(View view) {
        doOnClicked("ok");
    }

    public void onExitButtonClicked(View view) {
        this.finish();
    }
}