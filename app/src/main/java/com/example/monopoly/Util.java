package com.example.monopoly;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class Util {

    private static Sensor mAccelerometer;


    public static SensorManager createSensorManager(GameBoardActivity activity){
        return (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    public static Sensor getSensor(SensorManager sensorManager, int type){
        return sensorManager.getDefaultSensor(type);
    }

    public static ShakeDetector getShakeDetector(GameBoardActivity activity){
        return new ShakeDetector(activity);
    }


}
