package com.example.dbfitnesworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class stepCounter extends AppCompatActivity implements SensorEventListener {

    TextView steps;
    SensorManager sm;
    boolean run = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        steps = findViewById(R.id.stepcounter);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        run = true;
        Sensor count = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(count!=null)
        {
            sm.registerListener(this,count,sm.SENSOR_DELAY_UI);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        run = false;
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        steps.setText(String.valueOf(sensorEvent.values[0]) + "/10,000");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
