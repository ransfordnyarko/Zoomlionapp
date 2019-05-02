package com.example.workingproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public static TextView info, resultView, test;
    private ArrayList<String> codes, emails;
    String userID;

    private Button scan, add,update;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Member member;
    Information information;
    int no_products;
    double  newamount;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        scan = findViewById(R.id.ScanButton);
        add = findViewById(R.id.addItem_btn);
        update = findViewById(R.id.Updatebtn);
        resultView = findViewById(R.id.PaymentBalanceText);
        test = findViewById(R.id.textView3);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(HomeActivity.this,"Successfully signed in",Toast.LENGTH_SHORT);
                }
            }
        };

        reference = FirebaseDatabase.getInstance().getReference().child("Member");
        firebaseUser = firebaseAuth.getCurrentUser();
        userID =  firebaseUser.getUid();

        member = new Member();
        information = new Information();

        onStart();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanActvity.class));

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = info.getText().toString().trim();
                String code = resultView.getText().toString().trim();
                readData();


                member.setMail(email);
                member.setCode(code);
                reference.push().setValue(member);
                Toast.makeText(HomeActivity.this, "Data inserted sucessfully", Toast.LENGTH_SHORT);



            }

        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id = firebaseAuth.getCurrentUser().getUid();
                        Information user = dataSnapshot.child(id).getValue(Information.class);

                        String email = info.getText().toString().trim();


                        if (user.getAmount() == 0 ){
                            user.setAmount(0.2);
                            user.setNo_products(1);
                            reference.child(id).setValue(user);
                            text = "Your remaining Amount is GHc" + user.getAmount();
                            test.setText("Your remaining Amount is GHc" + user.getAmount());
                            //newamount=0.20;
                            //no_products =1;


                        }
                        else {
                            user.setAmount(user.getAmount() + 0.20);
                            user.setNo_products(user.getNo_products() + 1);
                            reference.child(id).setValue(user);
                            text = "Your remaining Amount is GHc" + user.getAmount();
                            test.setText("Your remaining Amount is GHc" + information.getAmount());
                        }
                        text = "Your remaining Amount is GHc" + user.getAmount();
                        test.setText("Your remaining Amount is GHc" + user.getAmount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        test.setText(text);


    }

    public void readData() {
        reference.child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> members = new ArrayList<>();
                Member member1 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .getValue(Member.class);
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Member member = dsp.getValue(Member.class);
                    members.add(member.getCode());
                }

                codes = members;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void UpdateData(){
        reference.child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Integer> amount = new ArrayList<>();
                Information member1 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .getValue(Information.class);
                newamount = member1.getAmount();
                no_products = member1.getNo_products();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        info = findViewById(R.id.UserText);
        if (user != null) {
            String email = user.getEmail();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
            info.setText(email);

        }
    }


}
