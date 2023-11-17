package com.example.tp3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button sendBtn;
    private EditText username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        sendBtn = findViewById(R.id.send);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);

        sendBtn.setOnClickListener(v ->{
            writeNewUser(username.getText().toString(), email.getText().toString());

        });
    }

    public void writeNewUser(String username, String email) {
        User user = new User(username, email);

        mDatabase.child("users").setValue(user).addOnSuccessListener((OnSuccessListener) documentReference -> {
                            Log.d(TAG, "DocumentSnapshot added with ID: ");
                    Intent intent = new Intent(this, DisplayDataActivity.class);
                    this.startActivity(intent);
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });;
    }
}