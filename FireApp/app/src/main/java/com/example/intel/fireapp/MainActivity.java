package com.example.intel.fireapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button btnRegis ;
    private EditText email, pass;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        btnRegis = (Button) findViewById(R.id.login);

        email = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

    }


    public void onClick(View view) {
        String emaill = email.getText().toString().trim();
        String pasw = pass.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(emaill,pasw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this, "berhasiiillll", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onClick2(View view){
        Intent i = new Intent(getApplicationContext(),register.class);
        startActivity(i);
    }
}
