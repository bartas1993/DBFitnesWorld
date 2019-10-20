package com.example.dbfitnesworld;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomDialougeAddExercise extends AppCompatActivity {

    public int reps = 1;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.addworkout);
        final EditText rep_count = (EditText)findViewById(R.id.repinput);
        Button plus_reps = (Button)findViewById(R.id.plusrep);
        Button minus_reps = (Button)findViewById(R.id.minusrep);
        Toast.makeText(getApplicationContext(),"IT WORKS",Toast.LENGTH_LONG).show();

        rep_count.setText(String.valueOf(reps));
        plus_reps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rep_count.setText(reps++);
            }
        });
        minus_reps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rep_count.setText(reps--);
            }
        });


    }
}
