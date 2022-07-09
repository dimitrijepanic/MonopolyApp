package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.example.monopoly.databinding.ActivityGameBoardBinding;
import com.example.monopoly.databinding.ActivityMainBinding;

public class GameBoardActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private ShakeDetector shakeDetector;
    private MediaPlayer mediaPlayer;

    public static boolean playMusic = true;
    public static int startingMoney = 1500;

    private ActivityGameBoardBinding binding;
    private Canvas canvas;

    public static int centerMoney = 0;
    private static Player [] players;
    private static int [] alphaPlayerColor = {0x80FF0000, 0x7E0000FF, 0x80FFFF00, 0x80008000};
    private static int fieldSize = 32;
    private Map map = new Map();
    private int currentPlayer = 0;

    public static String player1Name;
    public static String player2Name;
    public static String player3Name;
    public static String player4Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        centerMoney = 0;
        System.out.println(startingMoney + "");
        sensorManager = Util.createSensorManager(this);
        sensor = Util.getSensor(sensorManager, Sensor.TYPE_ACCELEROMETER);
        shakeDetector = Util.getShakeDetector(this);
        shakeDetector.setMedia(mediaPlayer, playMusic);

        map.initMap();
        createPlayers(4);
        paintPlayers(4);
        setStartPlayer();
        setPlayerMoney();

        binding.throwDice.setOnClickListener( v -> {
            enableThrowing();
        });

        binding.endGame.setOnClickListener(v->{
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        binding.player1Card.setOnClickListener(v->{
            showPlayerDialog(0);
        });
        binding.player2Card.setOnClickListener(v->{
            showPlayerDialog(1);
        });
        binding.player3Card.setOnClickListener(v->{
            showPlayerDialog(2);
        });
        binding.player4Card.setOnClickListener(v->{
            showPlayerDialog(3);
        });
    }

    public void shakeDetected(){
        sensorManager.unregisterListener(shakeDetector);
        throwDice();
    }

    private void enableThrowing(){
        shakeDetector.setUp();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shaking_sound);
        shakeDetector.setMedia(mediaPlayer, playMusic);
        sensorManager.registerListener(shakeDetector, sensor, SensorManager.SENSOR_DELAY_UI);

    }

    private void showPlayerDialog(int id){
        PlayerDialog dialog = new PlayerDialog(this, players[id], this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private int throwOneDice() {
        return (int) ((Math.random() * (6 - 1)) + 1);
    }

    private void setPlayerMoney(){
        binding.player1Money.setText(startingMoney + "");
        binding.player2Money.setText(startingMoney + "");
        binding.player3Money.setText(startingMoney + "");
        binding.player4Money.setText(startingMoney + "");
    }

    private void setStartPlayer(){
        binding.player1Card.setBackgroundColor(players[0].getColor());
    }


    private void throwDice(){

        checkWinner();
        // VIDI AKO DVA Puta baci isti broj
        boolean throwAgain = true;
        int random1 = 1, random2= 1;
        if(players[currentPlayer].getInJail() > -1){
            random1 = throwOneDice();
            random2 = throwOneDice();
            if(random1 == random2){
                Toast.makeText(this, "You are out of jail!", Toast.LENGTH_SHORT).show();
                players[currentPlayer].setInJail(-1);
                throwAgain = false;
            } else {
                players[currentPlayer].setInJail(players[currentPlayer].getInJail() + 1);
                if(players[currentPlayer].getInJail() == 3){
                    Toast.makeText(this, "You are out of jail!", Toast.LENGTH_SHORT).show();
                    players[currentPlayer].setInJail(-1);
                    throwAgain = false;
                } else {
                    currentPlayer = (currentPlayer + 1) % 4;
                    setBackground();
                    return;
                }
            }

        }

        if(throwAgain) {
             random1 = throwOneDice();
             random2 = throwOneDice();
        }

        System.out.println(random1 + " " + random2);

        int newField = (players[currentPlayer].getField() + random1 + random2 ) % 40;
        // ako se prodje 0 dodaj mu 200
        int oldField = players[currentPlayer].getField();
        players[currentPlayer].setField(newField);

        Field f = map.fields[newField];
        if(newField < oldField){
            players[currentPlayer].addMoney(200);
            Toast.makeText(this, "Crossed the start +200$", Toast.LENGTH_SHORT).show();
        }

        if(f.getType() == Field.Type.START){}
        else if(f.getType() == Field.Type.JAIL){}
        else if(f.getType() == Field.Type.PARKING){
            Toast.makeText(this, "Collect money from the center! " + centerMoney+ "$", Toast.LENGTH_SHORT).show();
            players[currentPlayer].addMoney(centerMoney);
            centerMoney = 0;
            changeMoney();
        } else if(f.getType() == Field.Type.GO_TO_JAIL){
            players[currentPlayer].setInJail(0);
            players[currentPlayer].setField(10);
            Toast.makeText(this, "You are going to prison..", Toast.LENGTH_SHORT).show();
        } else {
            FieldDialog dialog = new FieldDialog(this, f, players[currentPlayer], this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        if(players[currentPlayer].getMoney() <= 0){
            // moras da dodas da su ta polja free - prodji njegova i setuj ih
            // da su free

            Toast.makeText(this,
                    players[currentPlayer].getName() +" has lost..", Toast.LENGTH_SHORT).show();

        }

        currentPlayer = (currentPlayer + 1) % 4;

        while(players[currentPlayer].isHasLost()){
            currentPlayer = (currentPlayer + 1) % 4;
        }

        paintPlayers(4);
        setBackground();
    }

    private void checkWinner(){
        int winner = -1;
        int id = -1;
        for(int i = 0; i < 4;i++){
            if(!players[i].isHasLost()){
                winner++;
                id = i;
            }
        }

        if(winner == 1){
            binding.throwDice.setEnabled(false);
            Toast.makeText(this, "THE WINNER IS "+ players[id].getName(), Toast.LENGTH_LONG).show();
        }
    }

    public void changeMoney(){
        binding.player1Money.setText(players[0].getMoney() + "");
        binding.player2Money.setText(players[1].getMoney() + "");
        binding.player3Money.setText(players[2].getMoney() + "");
        binding.player4Money.setText(players[3].getMoney() + "");
    }

    public void setBackground(){
        binding.player1Card.setBackgroundColor(players[0].getBackgroundColor());
        binding.player2Card.setBackgroundColor(players[1].getBackgroundColor());
        binding.player3Card.setBackgroundColor(players[2].getBackgroundColor());
        binding.player4Card.setBackgroundColor(players[3].getBackgroundColor());

        switch(currentPlayer){
            case 0: binding.player1Card.setBackgroundColor(players[0].getColor()); break;
            case 1:binding.player2Card.setBackgroundColor(players[1].getColor()); break;
            case 2: binding.player3Card.setBackgroundColor(players[2].getColor()); break;
            case 3:binding.player4Card.setBackgroundColor(players[3].getColor()); break;
        }
    }

    private void createPlayers(int numPlayers){
        players = new Player[numPlayers];

        for(int i = 0; i < 4; i++){
            players[i] = new Player();
        }
        players[0].setField(0);
        players[0].setColor(Color.RED);
        players[0].setMoney(startingMoney);
        players[0].setName(player1Name);
        binding.player1Name.setText(player1Name);
        players[0].setBackgroundColor(0x80FF0000);

        players[1].setField(0);
        players[1].setColor(Color.BLUE);
        players[1].setMoney(startingMoney);
        players[1].setName(player2Name);
        binding.player2Name.setText(player2Name);
        players[1].setBackgroundColor(0x7E0000FF);

        players[2].setField(0);
        players[2].setColor(Color.YELLOW);
        players[2].setMoney(startingMoney);
        players[2].setName(player3Name);
        binding.player3Name.setText(player3Name);
        players[2].setBackgroundColor(0x80FFFF00);

        players[3].setField(0);
        players[3].setColor(Color.GREEN);
        players[3].setMoney(startingMoney);
        players[3].setName(player4Name);
        binding.player4Name.setText(player4Name);
        players[3].setBackgroundColor(0x80008000);
    }


    public void paintPlayers(int numPlayers){
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        bitmap = bitmap.copy(bitmap.getConfig(), true);
        canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        for(int i = 0; i < numPlayers; i++){
            paint.setColor(players[i].getColor());
            canvas.drawCircle(map.fields[players[i].getField()].getX(), map.fields[players[i].getField()].getY(), 10, paint);
            //canvas.drawRect(312,396,344,400, paint);
        }


        paint.setColor(Color.BLACK);
        paint.setTextSize(15);
        canvas.drawText("Parking money: " + centerMoney + "$",150,150, paint);

        binding.boardImg.setImageBitmap(bitmap);
        binding.boardImg.setBackgroundResource(R.drawable.board);
    }
}