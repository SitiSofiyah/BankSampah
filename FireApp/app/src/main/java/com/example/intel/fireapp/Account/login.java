package com.example.intel.fireapp.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.PreferencesUtility;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private EditText username, password;
    RelativeLayout loginForm;
    ProgressBar prolog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginForm = findViewById(R.id.loginForm);
        prolog = (ProgressBar) findViewById(R.id.prologin);

        // Check if UserResponse is Already Logged In
        if(SaveSharedPreference.getLoggedStatusPK(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), HomePK.class);
            startActivity(intent);
            finish();
        }else if(SaveSharedPreference.getLoggedStatusTR(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), Home_tr.class);
            startActivity(intent);
            finish();
        }else if(SaveSharedPreference.getLoggedStatusAnggota(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), Home_Anggota.class);
            startActivity(intent);
            finish();
        }else {
            loginForm.setVisibility(View.VISIBLE);
        }
    }


    public void onClick2(View view){
        Intent i = new Intent(getApplicationContext(),register.class);
        startActivity(i);
    }

    public void login(View view) {
        if(username.getText().toString()=="" || password.getText().toString()==""){
            Snackbar.make(findViewById(R.id.login), "Isikan username dan password anda !",Snackbar.LENGTH_LONG).show();
        }else{
            prolog.setVisibility(View.VISIBLE);
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            String nama = username.getText().toString();
            final String pass = password.getText().toString();
            final Query query = databaseReference.child("users").orderByChild("nama").equalTo(nama);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                            User login = userSnapshot.getValue(User.class);

                            if(login.getPassword().equals(pass)){
                                if(login.getLevel().equals("Tukang Rombeng")){
                                    SaveSharedPreference.setLoggedInTR(getApplicationContext(), true);
                                    SaveSharedPreference.setId(getApplicationContext(),login.getId());
                                    Intent i = new Intent(getApplicationContext(),Home_tr.class);
                                    startActivity(i);
                                    finish();

                                }else if (login.getLevel().equals("Pengepul Kecil")){
                                    SaveSharedPreference.setLoggedInPK(getApplicationContext(), true);
                                    SaveSharedPreference.setId(getApplicationContext(),login.getId());
                                    Intent i = new Intent(getApplicationContext(),HomePK.class);
                                    startActivity(i);
                                    finish();
                                }else{
                                    SaveSharedPreference.setLoggedInAnggota(getApplicationContext(), true);
                                    SaveSharedPreference.setId(getApplicationContext(),login.getId());
                                    Intent i = new Intent(getApplicationContext(),Home_Anggota.class);
                                    startActivity(i);
                                    finish();
                                }
                            }else{
                                Toast.makeText(login.this, "Akun tidak tersedia!", Toast.LENGTH_LONG).show();
                            }

                           // Toast.makeText(login.this, "nama anda : "+login.getNama(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
