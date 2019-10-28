package com.example.dbfitnesworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TextView username,level,powerlevel,strlev,endlev,speedlev,datecal;
    DatabaseReference databaseRef,databaseRef2,databaseReference3,databaseReference4,databaseReference5,databaseReference6,databaseReference7,databaseReference8;
    ImageButton menumain;
    DatePicker picker;
    FirebaseAuth mauth;
    ImageView profilePic;
    TextView lavelPower;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int reps = 0;
    double multiplier = 1000;
    double weight = 0;
    double a,b,c,d,e,f;
    ListView list;
    double speed,endurance,str;
    int exp = 0;
    public FirebaseAuth mAuth;
    public DatabaseReference myref;
    String date_n = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date());
    ProgressBar levelendurance,levelspeed,levelstrength;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    LinearLayout lm;
    int intprog;
    int motivProg;
    CalendarView calendarView;
    String id = user.getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        calendarView = findViewById(R.id.dates);
        datecal = findViewById(R.id.datecal);
        strlev = findViewById(R.id.strlev);
        menumain = findViewById(R.id.menumain);
        endlev = findViewById(R.id.levendu);
        speedlev = findViewById(R.id.speedlev);
        username = findViewById(R.id.user);
        profilePic = findViewById(R.id.profile);
        level = findViewById(R.id.level);
        powerlevel = findViewById(R.id.levelstat);
        levelendurance = findViewById(R.id.levelend);
        levelspeed = findViewById(R.id.levelspeed);
        levelstrength = findViewById(R.id.levelstrength);

        lm = findViewById(R.id.layoutMain);
        levelendurance.setMax(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            levelendurance.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
            levelstrength.setProgressTintList(ColorStateList.valueOf(Color.RED));
            levelspeed.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }
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

        databaseRef2 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Level");
        databaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int leve = dataSnapshot.getValue(Integer.class);
                level.setText(String.valueOf(leve));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference6 =FirebaseDatabase.getInstance().getReference("Users").child(id).child("Workouts").child(datecal.getText().toString());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                addExercise();
               int a = i1+=1;
               String getDates = i2+"-"+a+"-"+i;
               Toast.makeText(getApplicationContext(),getDates,Toast.LENGTH_LONG).show();
               datecal.setText(getDates);
                lm.removeAllViews();
               databaseReference8 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Workouts").child(datecal.getText().toString());
               databaseReference8.addChildEventListener(new ChildEventListener() {
                   @Override
                   public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                       for(DataSnapshot ds: dataSnapshot.getChildren()){

                           String [] a = {databaseReference8.push().getKey()};
                           s = (String) ds.getValue();
                           final TextView [] workouts = new TextView[10];
                           for(int i=0;i<a.length;i++)
                           {

                               workouts[i] = new TextView(getApplicationContext());
                               LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
                               workouts[i].setText(s);
                               workouts[i].setTextSize(20);
                               params.setMarginStart(10);
                               params.setMargins(30,0,0,0);
                               workouts[i].setLayoutParams(params);
                               lm.addView(workouts[i]);
                           }



                       }

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
            }
        });



        databaseReference6.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String [] a = {databaseReference6.push().getKey()};
                    s = ds.getValue(String.class);

                    final TextView [] workouts = new TextView[10];
                    for(int i=0;i<a.length;i++)
                    {
                        workouts[i] = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
                        workouts[i].setText(s);
                        workouts[i].setTextSize(20);
                        params.setMarginStart(10);
                        params.setMargins(30,0,0,0);
                        workouts[i].setLayoutParams(params);
                        lm.addView(workouts[i]);
                    }



                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                lm.removeAllViews();
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



        databaseReference3 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Endurance");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                endurance = dataSnapshot.getValue(Double.class);
                levelendurance.setProgress((int) endurance);
                endlev.setText(String.valueOf(endurance));
                double sum = endurance+speed+str*multiplier;
                powerlevel.setText(String.valueOf(sum));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference4 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Speed");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                speed = dataSnapshot.getValue(Double.class);
                levelspeed.setProgress((int) speed);
                speedlev.setText(String.valueOf(speed));
                double sum = endurance+speed+str*multiplier;
                powerlevel.setText(String.valueOf(sum));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference5 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("UserExperience").child("Strength");
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                str = dataSnapshot.getValue(Double.class);
                levelstrength.setProgress((int) str);
                strlev.setText(String.valueOf(str));
                double sum = endurance+speed+str*multiplier;
                powerlevel.setText(String.valueOf(sum));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        menumain.setOnClickListener(new View.OnClickListener() {
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
        ArrayAdapter<String> arrayAdapterBody = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.ExerciseType));
        final ArrayAdapter<String> arrayAdapterExerciseBodybuilding = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.ExerciseTypeBodyB));
        final ArrayAdapter<String> arrayAdapterExerciseFF = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.ExerciseTypeFF));

        Toast.makeText(getApplicationContext(), "Add Workout", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(this);
        final View CustomLayout = getLayoutInflater().inflate(R.layout.addworkout,null);
        Button increasereps = CustomLayout.findViewById(R.id.plusrep);
        Button decreasereps = CustomLayout.findViewById(R.id.minusrep);
        final EditText repss = CustomLayout.findViewById(R.id.repinput);
        final Spinner routing = CustomLayout.findViewById(R.id.routine);
        final Spinner exercise = CustomLayout.findViewById(R.id.exercisename);
        final SeekBar intensity = CustomLayout.findViewById(R.id.intensity);
        final TextView display = CustomLayout.findViewById(R.id.display);
        final SeekBar motivation = CustomLayout.findViewById(R.id.motivation);
        final TextView motTxt = CustomLayout.findViewById(R.id.motivationtxt);
        final TextView distance = CustomLayout.findViewById(R.id.distance);
        final TextView intenText = CustomLayout.findViewById(R.id.intensitytxt);
        final TextView addNotes = CustomLayout.findViewById(R.id.addnotes);
        final Button confirmW = CustomLayout.findViewById(R.id.addWorkout);
        Button increaserw = CustomLayout.findViewById(R.id.increaseweight);
        Button decreasew = CustomLayout.findViewById(R.id.minusweight);

        Button confirm = CustomLayout.findViewById(R.id.confirmAdd);
        final EditText weightinput = CustomLayout.findViewById(R.id.weigtinput);
        dialog.setContentView(CustomLayout);
        routing.setAdapter(arrayAdapterBody);
        motivation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                motivProg = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(motivProg<25)
                {
                    motTxt.setText("Low");
                }
                if(motivProg>25&& motivProg<70)
                {
                    motTxt.setText("Moderate");
                }
                if(motivProg>70)
                {
                    motTxt.setText("High");
                }

            }
        });
        confirmW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        intensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                intprog = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(intprog<=25)
                {
                    intenText.setText("Low ");
                }
                if(intprog>25&&intprog<=50)
                {
                    intenText.setText("Medium ");
                }
                if(intprog>50 && intprog<=75)
                {
                    intenText.setText("High ");
                }
                if(intprog>75)
                {
                    intenText.setText("Extreme ");
                }
            }
        });


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
                final DatabaseReference refStr = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("UserExperience").child("Strength");
                final DatabaseReference refSpeed = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("UserExperience").child("Speed");
                final DatabaseReference refEnd = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("UserExperience").child("Endurance");
                databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Workouts").child(datecal.getText().toString());
                HashMap<String,String> map = new HashMap<>();
                map.put("Routine",routing.getSelectedItem().toString());
                map.put("Exercise Name", exercise.getSelectedItem().toString());
                map.put("Date",datecal.getText().toString());
                map.put("Reps: ",repss.getText().toString() + " Repetitions");
                map.put("Weight: "+"Weight:", weightinput.getText().toString()+" Weight(Kg)"+"\n"+"__________________________________________________" );
                map.put("Notes: "+ "Notes:", addNotes.getText().toString());
                map.put("Distance: "+"Distance: ",distance.getText().toString()+" Distance(m)");
                map.put("Intensity: "+"Intensity: ",String.valueOf(intensity.getProgress())+" --Intensity");
                map.put("Motivation: "+"Motivation: ",String.valueOf(motivation.getProgress())+"--Motivation");
                display.append(routing.getSelectedItem().toString()+"\n"+exercise.getSelectedItem().toString()
                        +"\n"+repss.getText()+"\n"+weightinput.getText()+"\n"+addNotes.getText()+"\n"+distance.getText()
                        +"\n"+String.valueOf(intensity.getProgress())+"\n"+String.valueOf(motivation.getProgress())+"\n"+"_________________________"+"\n");
                databaseReference7 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("CompleteWorkouts");

                ///////////////STRENGTH VALUES//////////////
                refStr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        a = dataSnapshot.getValue(Double.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(routing.getSelectedItem().toString().equals("Weight Lifting")){refStr.setValue(str+=2);}
                if(routing.getSelectedItem().toString().equals("Free Running")){refStr.setValue(str+=0.5);}
                if(routing.getSelectedItem().toString().equals("Martial Arts")){refStr.setValue(str+=0.5);}
                if(routing.getSelectedItem().toString().equals("Calisthenics")){refStr.setValue(str+=1);}
                if(routing.getSelectedItem().toString().equals("Long Distance Run")){refStr.setValue(str+=0);}
                if(routing.getSelectedItem().toString().equals("Power Lifting")){refStr.setValue(str+=4);}
                refStr.setValue(str+=0.5);
                //////////////STRENGTH VALUES///////////////


                ///////////////SPEED VALUES//////////////
                refSpeed.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        c = dataSnapshot.getValue(Double.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if(routing.getSelectedItem().toString().equals("Weight Lifting")){refSpeed.setValue(speed+=0);}
                if(routing.getSelectedItem().toString().equals("Free Running")){refSpeed.setValue(speed+=2);}
                if(routing.getSelectedItem().toString().equals("Martial Arts")){refSpeed.setValue(speed+=2);}
                if(routing.getSelectedItem().toString().equals("Calisthenics")){refSpeed.setValue(speed+=2);}
                if(routing.getSelectedItem().toString().equals("Long Distance Run")){refSpeed.setValue(speed+=3);}
                if(routing.getSelectedItem().toString().equals("Power Lifting")){refSpeed.setValue(speed+=0.5);}
                //////////////SPEED VALUES///////////////

                ///////////////ENDURANCE VALUES//////////////
                refEnd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        d = dataSnapshot.getValue(Double.class);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(routing.getSelectedItem().toString().equals("Weight Lifting")){refEnd.setValue(endurance+=0.5);}
                if(routing.getSelectedItem().toString().equals("Free Running")){refEnd.setValue(endurance+=1);}
                if(routing.getSelectedItem().toString().equals("Martial Arts")){refEnd.setValue(endurance+=2);}
                if(routing.getSelectedItem().toString().equals("Calisthenics")){refEnd.setValue(endurance+=2);}
                if(routing.getSelectedItem().toString().equals("Long Distance Run")){refEnd.setValue(endurance+=1.5);}
                if(routing.getSelectedItem().toString().equals("Power Lifting")){refEnd.setValue(endurance+=0.5);}

                //////////////ENDURANCE VALUES///////////////






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
class Experience {
    private double endurance,speed,strength;

    public Experience(int endurance, int speed, int strength) {
        this.endurance = endurance;
        this.speed = speed;
        this.strength = strength;
    }

    public double getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}

