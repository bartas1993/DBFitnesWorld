package com.example.dbfitnesworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Spinner spinmaster,bodytype,activitylevel;
    TextView masterbox;
    ImageView avatar;
    public EditText username,password,repeatpassword,nick,age,weight,height;
    public Button next;
    public FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef,databaseRef2,databaseRef3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        next = findViewById(R.id.registerbutton);
        age = findViewById(R.id.userage);
        weight = findViewById(R.id.userweight);
        height = findViewById(R.id.userheight);
        username = findViewById(R.id.username);
        nick = findViewById(R.id.nickname);
        password = findViewById(R.id.password);
        repeatpassword = findViewById(R.id.passwordrepeat);
        avatar = findViewById(R.id.avatarmaster);
        bodytype = findViewById(R.id.bodytype);
        spinmaster = findViewById(R.id.spinnermaster);
        activitylevel = findViewById(R.id.activitylv);
        masterbox = findViewById(R.id.masterbox);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final String user = username.getText().toString();
                String pass = password.getText().toString();
                final String nickname = nick.getText().toString();
                final String agee = age.getText().toString();
                final String weightt = weight.getText().toString();
                final String heightt = height.getText().toString();
                mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser userr = mAuth.getCurrentUser();
                        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userr.getUid());
                        HashMap<String,String> map = new HashMap<>();
                        final HashMap<String,Double> map2 = new HashMap<>();
                        map.put("Nickname",nickname);
                        map2.put("Level",1.0);
                        map.put("UserMail",user);
                        map.put("Difficulty",spinmaster.getSelectedItem().toString());
                        map.put("UserAge",agee);
                        map.put("UserWeight",weightt);
                        map.put("UserHeight",heightt);
                        map.put("BodyType",bodytype.getSelectedItem().toString());
                        map.put("ActivityLevel",activitylevel.getSelectedItem().toString());
                        map2.put("Strength",0.05);
                        map2.put("Speed",0.05);
                        map2.put("Endurance",0.05);
                        map2.put("OverallLevel",0.0);
                        map2.put("Multiplier",1000.0);
                        databaseRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUser userr = mAuth.getCurrentUser();
                                Intent i = new Intent(RegisterActivity.this,Home.class);
                                startActivity(i);
                                databaseRef2 = FirebaseDatabase.getInstance().getReference("Users").child(userr.getUid()).child("UserExperience");
                                databaseRef2.setValue(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"User Experience Added",Toast.LENGTH_LONG).show();
                                    }
                                });
                                Toast.makeText(RegisterActivity.this,"Please Log In",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });






                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            {

            }
            }
        });




        //array adapter for spinner
        ArrayAdapter arrayAdapterBody = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.BodyType));
        ArrayAdapter arrayAdapterActivity = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.activitylevel));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.avatars));
        spinmaster.setAdapter(arrayAdapter);
        activitylevel.setAdapter(arrayAdapterActivity);
        bodytype.setAdapter(arrayAdapterBody);
        spinmaster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    masterbox.setText("He's great for novice \n Train with him if you think you hava long way to go");
                    avatar.setImageResource(R.drawable.kriltwo);
                }
                if (i == 1) {
                    masterbox.setText("He's great for somebody with some experience \n Train with him if you think you have what it takes to train little bit harder");
                    avatar.setImageResource(R.drawable.ygohan);
                }
                if (i == 2) {
                    masterbox.setText("You up for some chalenge? \n With his more challenging routine you be sure to become a real deal");
                    avatar.setImageResource(R.drawable.pic);
                }
                if (i == 3) {
                    masterbox.setText("Now we talk...\n He'll make sure you be on your knees after training, only for experienced!");
                    avatar.setImageResource(R.drawable.ugohan);
                }
                if (i == 4) {
                    masterbox.setText("No mercy! \n Real hell awaits!, you be cut in no time! only for pros!");
                    avatar.setImageResource(R.drawable.vegeta);
                }
                if (i == 5) {
                    avatar.setImageResource(R.drawable.db2);
                    masterbox.setText("Pro level \n Balance between strength speed and endurance at highest level");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //array adapter for spinner
        //button next

    }
}
