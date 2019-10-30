package com.example.dbfitnesworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Wall extends AppCompatActivity {

    ScrollView scrollView;
    TextView user;
    EditText chattext;
    LinearLayout posts;
    ImageView send;
    FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseRef,databaseRef2;
    String id = userID.getUid();
    String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        chattext = findViewById(R.id.chatinput);
        scrollView = findViewById(R.id.scrollablea);
        send = findViewById(R.id.sendPost);
        posts = findViewById(R.id.postsPublic);
        user = findViewById(R.id.getusername);
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Nickname");
        databaseRef2 = FirebaseDatabase.getInstance().getReference("Chat").child("Public");
        int down = scrollView.getBottom();
        scrollView.scrollTo(0,down);
        databaseRef2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int down = scrollView.getBottom();
                scrollView.scrollTo(0,down);
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String [] a = {databaseRef2.push().getKey()};
                    s = (String) ds.getValue();
                    final TextView [] workouts = new TextView[10];
                    for(int i=0;i<a.length;i++)
                    {

                        workouts[i] = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
                        workouts[i].setText(s);
                        params.gravity = Gravity.LEFT;
                        params.topMargin = 5;
                        workouts[i].setTextColor(ColorStateList.valueOf(Color.DKGRAY));
                        workouts[i].setTextSize(20);
                        params.setMarginStart(20);
                        workouts[i].setLayoutParams(params);
                        posts.addView(workouts[i]);

                        workouts[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });



                    }}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String usernam = dataSnapshot.getValue(String.class);
                user.setText(String.valueOf(usernam));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String [] a = {databaseRef2.push().getKey()};
                String text = chattext.getText().toString();
                String username = user.getText().toString();
                final TextView [] workouts = new TextView[10];
                for(int i=0;i<a.length;i++)
                {
                    workouts[i] = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
                    workouts[i].setText(username+"\n"+date_n+"\n"+text+"\n"+"_____________________________"+"\n"+"\n");

                    workouts[i].setLayoutParams(params);
                    final HashMap<String,String> map = new HashMap<>();
                    map.put("Username:",username.toUpperCase());
                    map.put("Date:","Date: " +String.valueOf(date_n));
                    map.put("Word:",text+"\n"+"_______________________");
                    databaseRef2.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            chattext.setText("");
                            int down = scrollView.getBottom();
                            scrollView.scrollTo(0,down);
                        }
                    });


                }


            }
        });

    }

}
