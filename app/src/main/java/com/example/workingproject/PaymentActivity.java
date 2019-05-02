package com.example.workingproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
    EditText amount;
    Button pay;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    Information information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        amount = findViewById(R.id.AmountText);
        reference = FirebaseDatabase.getInstance().getReference().child("Member");
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(PaymentActivity.this,"Successfully signed in",Toast.LENGTH_SHORT);
                }
            }
        };
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        pay = findViewById(R.id.button);

        information = new Information();


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt = amount.toString();
                double  am = Double.parseDouble(amt);
                String email = HomeActivity.info.getText().toString().trim();

                    information.setEmail(email);
                    information.setAmount(information.getAmount() - am );
                    information.setNo_products(information.getNo_products());
                    reference.setValue(information);
                }




        });
    }


}
