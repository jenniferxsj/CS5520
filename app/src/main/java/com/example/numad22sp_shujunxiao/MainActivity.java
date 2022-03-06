package com.example.numad22sp_shujunxiao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button_clicky);
        Button button2 = findViewById(R.id.about_me_button);
        Button button3 = findViewById(R.id.button_link);
        Button button4 = findViewById(R.id.button_location);
        Button button5 = findViewById(R.id.btn_web);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.about_me_button:
                intent = new Intent(this, AboutMeActivity.class);
                startActivity(intent);
                break;
            case R.id.button_link:
                intent = new Intent(this, LinkCollectorActivity.class);
                startActivity(intent);
                break;
            case R.id.button_clicky:
                intent = new Intent(this, ButtonActivity.class);
                startActivity(intent);
                break;
            case R.id.button_location:
                intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_web:
                intent = new Intent(this, WebServiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}