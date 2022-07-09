package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.monopoly.databinding.ActivityStartGameBinding;

public class StartGameActivity extends AppCompatActivity {

    ActivityStartGameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.butt.setOnClickListener(v->{

            GameBoardActivity.centerMoney = 0;
            GameBoardActivity.player1Name = binding.player1Name.getText().toString();
            GameBoardActivity.player2Name = binding.player2Name.getText().toString();
            GameBoardActivity.player3Name = binding.player3Name.getText().toString();
            GameBoardActivity.player4Name = binding.player4Name.getText().toString();
            Intent i = new Intent(this, GameBoardActivity.class);
            startActivity(i);
        });
    }
}