package com.example.yamba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class StatusActivity extends AppCompatActivity {
    Button tweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        tweet = findViewById(R.id.buttonTweet);
    }
}
