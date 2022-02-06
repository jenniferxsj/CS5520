package com.example.numad22sp_shujunxiao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button_clicky);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openButtonActivity();
            }
        });
    }

    public void showMsg(View view) {
        Toast.makeText(this, "Name: Shujun Xiao\nEmail: xiao.shu@northeastern.edu", Toast.LENGTH_LONG).show();
    }

    public void openButtonActivity() {
        Intent intent = new Intent(this, ButtonActivity.class);
        startActivity(intent);
    }
}