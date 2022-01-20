package com.example.numad22sp_shujunxiao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMsg(View view) {
        Toast.makeText(this, "Name: Shujun Xiao\nEmail: xiao.shu@northeastern.edu", Toast.LENGTH_LONG).show();
    }
}