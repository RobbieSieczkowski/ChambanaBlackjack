package com.example.user.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Start New Game button */
    public void startNewGame(View view) {
        Intent intent = new Intent(this, DisplayNewGame.class);
        startActivity(intent);
    }

    /** Called when the user taps the Info button */
    public void info(View view) {
        Intent intent = new Intent(this, DisplayInfo.class);
        startActivity(intent);
    }
}
