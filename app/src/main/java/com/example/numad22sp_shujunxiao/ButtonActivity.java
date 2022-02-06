package com.example.numad22sp_shujunxiao;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        textView = findViewById(R.id.textView2);

        Button buttonA = findViewById(R.id.button_a);
        Button buttonB = findViewById(R.id.button_b);
        Button buttonC = findViewById(R.id.button_c);
        Button buttonD = findViewById(R.id.button_d);
        Button buttonE = findViewById(R.id.button_e);
        Button buttonF = findViewById(R.id.button_f);

        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
        buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String pressed = getResources().getString(R.string.pressed);
        switch (view.getId()) {
            case R.id.button_a:
                String a = pressed + getResources().getString(R.string.a);
                textView.setText(a);
                break;
            case R.id.button_b:
                String b = pressed + getResources().getString(R.string.b);
                textView.setText(b);
                break;
            case R.id.button_c:
                String c = pressed + getResources().getString(R.string.c);
                textView.setText(c);
                break;
            case R.id.button_d:
                String d = pressed + getResources().getString(R.string.d);
                textView.setText(d);
                break;
            case R.id.button_e:
                String e = pressed + getResources().getString(R.string.e);
                textView.setText(e);
                break;
            case R.id.button_f:
                String f = pressed + getResources().getString(R.string.f);
                textView.setText(f);
                break;
        }
    }
}