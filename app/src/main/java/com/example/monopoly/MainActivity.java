package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.example.monopoly.databinding.ActivityGameBoardBinding;
import com.example.monopoly.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startGame.setOnClickListener(v->{

            Intent i = new Intent(MainActivity.this, StartGameActivity.class);
            startActivity(i);
        }
        );

        binding.settings.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        });
    }
}