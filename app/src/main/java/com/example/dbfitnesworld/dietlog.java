package com.example.dbfitnesworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dietlog extends AppCompatActivity {

    CalendarView picker;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference dataRef,dataRef2,dataRef3;
    String id = user.getUid();
    TextView curW;
    TextView goalW;
    String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietlog);
        picker = findViewById(R.id.dietpicker);
        curW = findViewById(R.id.curentW);
        goalW = findViewById(R.id.goalW);
        dataRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserWeight");
        dataRef3 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserWeight").child(date_n);
        dataRef2 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserGoalWeight");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String getWeightCurent = dataSnapshot.getValue(String.class);
                curW.setText(String.valueOf(getWeightCurent));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        dataRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String getWeightCurent = dataSnapshot.getValue(String.class);
                goalW.setText(String.valueOf(getWeightCurent));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        curW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                Dialog dialog = new Dialog(dietlog.this);
                final View CustomLayout = getLayoutInflater().inflate(R.layout.addfood,null);
                final SearchView searchView = findViewById(R.id.selectfoodsearch);
                dialog.setContentView(CustomLayout);
                dialog.show();
            }
        });
    }
}
