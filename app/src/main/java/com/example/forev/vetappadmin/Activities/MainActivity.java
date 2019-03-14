package com.example.forev.vetappadmin.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.forev.vetappadmin.Fragments.HomeFragment;
import com.example.forev.vetappadmin.R;
import com.example.forev.vetappadmin.Utils.ChangeFragments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChangeFragments changeFragments = new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());


    }
}
