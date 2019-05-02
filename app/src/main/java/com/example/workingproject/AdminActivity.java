package com.example.workingproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class AdminActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
       final ListView listView = findViewById(R.id.adminList);

//       ValueEventListener valueEventListener = new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               ArrayList<String> members = new ArrayList<>();
//               Member member1 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                       .getValue(Member.class);
//               for (DataSnapshot dsp: dataSnapshot.getChildren()){
//                   Member member  = dsp.getValue(Member.class);
//                   members.add(member.getCode());
//               }
//
//               ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, members);
//               listView.setAdapter(adapter);
//
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError databaseError) {
//               Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT);
//           }
//       };
//
//       mDatabaseReference.addListenerForSingleValueEvent(valueEventListener);

        mDatabaseReference.child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Information> members = new ArrayList<>();
                Member member1 = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .getValue(Member.class);
                for (DataSnapshot dsp: dataSnapshot.getChildren()){
                    Information information  = dsp.getValue(Information.class);
                    members.add(information);
                }

                AdminAdapter adapter = new AdminAdapter(getBaseContext(), R.layout.activity_admin, members);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT);
            }
        });



    }
}
