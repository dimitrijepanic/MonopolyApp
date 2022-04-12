package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.example.monopoly.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Canvas canvas;

    private static int [] playerColor = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
    private static int [] alphaPlayerColor = {0x80FF0000, 0x7E0000FF, 0x80FFFF00, 0x80008000};
    private static int fieldSize = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setPlayers(4);
        setStartPlayer();

    }

    private void setStartPlayer(){
        binding.player1Card.setBackgroundColor(playerColor[0]);
    }

    private int throwDice(){
        return 0;
    }

    private void setPlayers(int numPlayers){
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        bitmap = bitmap.copy(bitmap.getConfig(), true);
        canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        for(int i = 0; i < numPlayers; i++){
            paint.setColor(playerColor[i]);
            canvas.drawCircle(370, 370, 10, paint);
            //canvas.drawRect(312,396,344,400, paint);
        }

        binding.boardImg.setImageBitmap(bitmap);
        binding.boardImg.setBackgroundResource(R.drawable.board);
    }
}