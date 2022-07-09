package com.example.monopoly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.monopoly.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.butt.setOnClickListener(v->{

            String money = binding.startMoney.getEditableText().toString();

            if(money.equals("")){
                GameBoardActivity.startingMoney = 1500;
            } else {

                try{
                    int m = Integer.parseInt(money);
                    GameBoardActivity.startingMoney = m;
                }catch(Exception e){
                    Toast.makeText(this, "Money needs to be a number", Toast.LENGTH_SHORT).show();
                    GameBoardActivity.startingMoney = 1500;
                    return;
                }
            }

            boolean music = true;
            if(binding.radioNo.isChecked()) music = false;

            GameBoardActivity.playMusic = music;

            String tresh = binding.shaketresh.getEditableText().toString();

            if(tresh.equals("")){
                ShakeDetector.SHAKE_THRESHOLD_GRAVITY = 2.7f;
            } else {

                try{
                    float m = Float.parseFloat(tresh);
                    ShakeDetector.SHAKE_THRESHOLD_GRAVITY = m;
                }catch(Exception e){
                    Toast.makeText(this, "Treshold needs to be a number", Toast.LENGTH_SHORT).show();
                    ShakeDetector.SHAKE_THRESHOLD_GRAVITY = 2.7f;
                    return;
                }
            }

            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();

        });

        binding.back.setOnClickListener(v->{
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

}