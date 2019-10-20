package com.example.dbfitnesworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TextView username,level,powerlevel;
    DatabaseReference databaseRef,databaseRef2,databaseReference3,databaseReference4,databaseReference5,databaseReference6;
    FirebaseAuth mauth;
    ImageView profilePic;
    TextView lavelPower;
    FirebaseUser user;
    int reps = 0;
    double weight = 0;
    int exp = 0;
    public FirebaseAuth mAuth;
    public DatabaseReference myref;
    Date date = Calendar.getInstance().getTime();
    ProgressBar levelendurance,levelspeed,levelstrength;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        username = findViewById(R.id.user);
        profilePic = findViewById(R.id.profile);
        level = findViewById(R.id.level);
        levelendurance = findViewById(R.id.levelend);
        levelspeed = findViewById(R.id.levelspeed);
        levelstrength = findViewById(R.id.levelstrength);
        levelendurance.setMax(1000);
        levelstrength.setMax(1000);
        levelspeed.setMax(1000);
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Nickname");
        databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String usernam = dataSnapshot.getValue(String.class);
                        username.setText(String.valueOf(usernam));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        databaseRef2 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Level");
        databaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String leve = dataSnapshot.getValue(String.class);
                level.setText(String.valueOf(leve));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Endurance");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double endurance = dataSnapshot.getValue(Double.class);
                levelendurance.setProgress((int) endurance);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference4 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Speed");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double speed = dataSnapshot.getValue(Double.class);
                levelspeed.setProgress((int) speed);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference5 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Strength");
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double str = dataSnapshot.getValue(Double.class);
                levelstrength.setProgress((int) str);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference6 = FirebaseDatabase.getInstance().getReference("Users").child(id);
        databaseReference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    Map<String,Object> map = (Map<String, Object>) data.getChildren();
                    Object getvalues = map.get("UserExperience");
                    int total = Integer.parseInt(String.valueOf(getvalues));
                    sum+=total*100;
                    powerlevel.setText(String.valueOf(sum));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),profilePic);
                popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(Home.this);

            }
        });
        }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "User Profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
              addExercise();
              return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
                System.exit(0);
                return true;
        }

        return false;
    }















    public void addExercise() {
        ArrayAdapter arrayAdapterBody = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.ExerciseType));
        final ArrayAdapter arrayAdapterExerciseBodybuilding = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.ExerciseTypeBodyB));
        final ArrayAdapter arrayAdapterExerciseFF = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.ExerciseTypeFF));

        Toast.makeText(getApplicationContext(), "Add Workout", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(this);
        View CustomLayout = getLayoutInflater().inflate(R.layout.addworkout,null);
        Button increasereps = CustomLayout.findViewById(R.id.plusrep);
        Button decreasereps = CustomLayout.findViewById(R.id.minusrep);
        final EditText repss = CustomLayout.findViewById(R.id.repinput);
        final Spinner routing = CustomLayout.findViewById(R.id.routine);
        final Spinner exercise = CustomLayout.findViewById(R.id.exercisename);

        Button increaserw = CustomLayout.findViewById(R.id.increaseweight);
        Button decreasew = CustomLayout.findViewById(R.id.minusweight);
        final Button addExercise = CustomLayout.findViewById(R.id.addExercise);
        Button confirm = CustomLayout.findViewById(R.id.confirmAdd);
        final EditText weightinput = CustomLayout.findViewById(R.id.weigtinput);
        dialog.setContentView(CustomLayout);
        routing.setAdapter(arrayAdapterBody);
        routing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    exercise.setAdapter(arrayAdapterExerciseBodybuilding);
                }
                if(i==1)
                {
                    exercise.setAdapter(arrayAdapterExerciseFF);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        increasereps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Increase",Toast.LENGTH_LONG).show();

                reps+=1;
                repss.setText(String.valueOf(reps));
            }
        });
        decreasereps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Increase",Toast.LENGTH_LONG).show();

                reps-=1;
                repss.setText(String.valueOf(reps));
                if(reps == -1)
                {
                    reps=1;
                }
            }
        });
        increaserw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Increase",Toast.LENGTH_LONG).show();

                weight+=0.25;
                weightinput.setText(String.valueOf(weight));
            }
        });
        decreasew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Increase",Toast.LENGTH_LONG).show();

                weight-=0.25;
                weightinput.setText(String.valueOf(weight));
                if(weight <0)
                {
                    weight=1;
                }
            }
        });
        increaserw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                weight+=1;
                weightinput.setText(String.valueOf(weight));
                return true;

            }
        });
        decreasew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                weight-=1;
                weightinput.setText(String.valueOf(weight));
                return true;

            }
        });

        dialog.setTitle("Create new Workout");
        dialog.show();


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Workouts").child(date.toString()).child(routing.getSelectedItem().toString()+"__"+date.toString()).child(exercise.getSelectedItem().toString());
                HashMap<String,String> map = new HashMap<>();

                map.put("Workout: ",routing.getSelectedItem().toString());
                map.put("Exercise: ",exercise.getSelectedItem().toString());
                map.put("Reps: ",repss.getText().toString());
                map.put("Weight: ", weightinput.getText().toString() );

                databaseRef.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(getApplicationContext(),"Workout Finished",Toast.LENGTH_LONG).show();


                    }
                });

            }
        });


    }

}

