package com.example.dbfitnesworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button register,login;
    FirebaseAuth mAuth;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.registerbtn);
        login = findViewById(R.id.loginbtno);
        username = findViewById(R.id.usernameo);
        password = findViewById(R.id.passwordo);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                mAuth.signInWithEmailAndPassword(user, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        updateUI(user);
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"error no user",Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
    }
});
    }

    private void updateUI(FirebaseUser user) {

        user.getUid();
        Intent i = new Intent(MainActivity.this,Home.class);
        startActivity(i);
        finish();
    }

    //@Override
    //public void onStart() {
      //  super.onStart();
        //// Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
       // if (currentUser != null) {
         //   updateUI(currentUser);
       // }

    //}

}
