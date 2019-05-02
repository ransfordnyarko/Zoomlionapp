package com.example.workingproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText mail, password;
    Button signup, login;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.user_login);
        password = findViewById(R.id.pass_login);
        signup = findViewById(R.id.signup_btn);
        login = findViewById(R.id.login_btn);

        reference = FirebaseDatabase.getInstance().getReference().child("Member");
        FirebaseApp.initializeApp(MainActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mail.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this,"Enter Mail", Toast.LENGTH_SHORT);
                }

                else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this,"Enter Password", Toast.LENGTH_SHORT);
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Information information = new Information(email, 0, 0);
                                        String userId = firebaseAuth.getCurrentUser().getUid();
                                        reference.child(userId).setValue(information);
                                        reference.child(userId).push().setValue(information);
                                        startActivity(new Intent(getApplicationContext(), Main2Activity.class ));
                                        Toast.makeText(MainActivity.this,"Succesfull", Toast.LENGTH_SHORT);

                                    } else {
                                        Toast.makeText(MainActivity.this,"Failed", Toast.LENGTH_SHORT);

                                    }

                                    // ...
                                }
                            });

                }



            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
                String pass = password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, Main2Activity.class ));
                                    Toast.makeText(MainActivity.this,"Succesfull", Toast.LENGTH_SHORT);

                                } else {
                                    Toast.makeText(MainActivity.this,"Failed", Toast.LENGTH_SHORT);

                                }

                                // ...
                            }
                        });

            }
        });



    }
}
