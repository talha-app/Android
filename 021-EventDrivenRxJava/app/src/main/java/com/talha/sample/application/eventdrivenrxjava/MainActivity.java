package com.talha.sample.application.eventdrivenrxjava;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding4.widget.RxTextView;

import io.reactivex.rxjava3.core.Observable;

public class MainActivity extends AppCompatActivity {

    TextView textViewMessage;


    private void initViews() {
        initMessageViews();
    }

    private void onMessageError(Throwable ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void updateMessageTextView(CharSequence text) {
        textViewMessage.setText(text);
    }

    private void initMessageViews() {
        textViewMessage = this.findViewById(R.id.mainActivityTextViewMessage);

        EditText editTextMessage = this.findViewById(R.id.mainActivityEditTextMessage);

        TextView textViewUpperMessage = this.findViewById(R.id.mainActivityTextViewUpperMessage);


        Observable<CharSequence> textChangeObservable = RxTextView.textChanges(editTextMessage)
                .filter(t -> t.length() > 3)
                .filter(t -> t.length() < 10);

        textChangeObservable
                .subscribe(text -> updateMessageTextView(text), ex -> this.onMessageError(ex));
        textChangeObservable.map(t -> t.toString().toUpperCase()).subscribe(t -> textViewUpperMessage.setText(t));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}