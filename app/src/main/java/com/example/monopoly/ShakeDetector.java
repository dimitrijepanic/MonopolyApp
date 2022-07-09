package com.example.monopoly;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.FloatMath;

public class ShakeDetector implements SensorEventListener {

    /*
     * The gForce that is necessary to register as shake.
     * Must be greater than 1G (one earth gravity unit).
     * You can install "G-Force", by Blake La Pierre
     * from the Google Play Store and run it to see how
     *  many G's it takes to register a shake
     */
    public static float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;


    private MediaPlayer mediaPlayer;
    private boolean playMusic = true;

    private GameBoardActivity activity;
    public ShakeDetector(GameBoardActivity activity){
        this.activity = activity;
    }
    public void setMedia(MediaPlayer player, boolean playMusic){
        mediaPlayer = player;
        this.playMusic = playMusic;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    int a ;
    public void setUp(){
        a = 0;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / 9.80665f;
            float gY = y / 9.80665f;
            float gZ = z / 9.80665f;

            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                a = 1;
                System.out.println("Shake detected");
                if(playMusic)
                mediaPlayer.start();

            } else if(gForce  < 1.4 && gForce > 0.7){
                if(a == 1){
                    System.out.println("Shaking stopped");
                    if(playMusic){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    activity.shakeDetected();
                }


        }
    }
}
