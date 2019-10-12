package com.example.yamba;

import android.os.Bundle;
import android.view.Menu;


import androidx.appcompat.app.AppCompatActivity;

public class StatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            StatusFragment fragment = new StatusFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_status, fragment, fragment.getClass().getSimpleName()).commit();
        }

        setContentView(R.layout.new_activity_status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}