package com.example.tp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DisplayDataActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private TextView username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);

        mDatabase.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Map<String,String> data=(HashMap<String, String>) task.getResult().getValue();
                    User user = new User(data.get("username"), data.get("email"));
                    username.setText(username.getText().toString() + user.username);
                    email.setText(email.getText().toString() + user.email);

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}