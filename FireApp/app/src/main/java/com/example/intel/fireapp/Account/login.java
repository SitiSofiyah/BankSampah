package com.example.intel.fireapp.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private String idUser;
    private Button login;
    private boolean hasil=false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        this.checkSavedCredentials();




        sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        saveCredentials(login.getNama(),login.getPassword(),login.getId(),login.getLevel());
                                        Intent i = new Intent(getApplicationContext(),Home_tr.class);
                                        i.putExtra("id", login.getId());
                                        startActivity(i);
                                        finish();
                                        // Toast.makeText(login.this, "Tukang Rombeng", Toast.LENGTH_LONG).show();
                                    }else if (login.getLevel().equals("Pengepul Kecil")){
                                        saveCredentials(login.getNama(),login.getPassword(),login.getId(),login.getLevel());
                                        Intent i = new Intent(getApplicationContext(),HomePK.class);
                                        i.putExtra("id", login.getId());
                                        startActivity(i);
                                        finish();
                                        // Toast.makeText(login.this, "Pengepul Kecil", Toast.LENGTH_LONG).show();
                                    }else{
                                        saveCredentials(login.getNama(),login.getPassword(),login.getId(),login.getLevel());
                                        Intent i = new Intent(getApplicationContext(),Home_Anggota.class);
                                        i.putExtra("id", login.getId());
                                        startActivity(i);
                                        finish();
                                        //Toast.makeText(login.this, "PageAnggota", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(login.this, "gaaadaaaa", Toast.LENGTH_LONG).show();
                                }

                                Toast.makeText(login.this, "nama anda : "+login.getNama(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    public void onClick2(View view){
        Intent i = new Intent(getApplicationContext(),register.class);
        startActivity(i);
    }

    private void checkSavedCredentials() {
        SharedPreferences handler = this.getPreferences(Context.MODE_PRIVATE);

        String username = handler.getString("username","");
        String password = handler.getString("password","");
        String level = handler.getString("level","");

        boolean loginCorrect = this.checkCredentials(username,password);

        if(loginCorrect){
            if(level=="PageAnggota"){
                Intent i = new Intent(getApplicationContext(),Home_Anggota.class);
                startActivity(i);
                finish();
            }else if(level=="Tukang Rombeng"){
                Intent i = new Intent(getApplicationContext(),Home_tr.class);
                startActivity(i);
                finish();
            }else{
                Intent i = new Intent(getApplicationContext(),HomePK.class);
                startActivity(i);
                finish();
            }
        }
    }

    private boolean checkCredentials(String username, final String password) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query query = databaseReference.child("users").orderByChild("nama").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                        User login = userSnapshot.getValue(User.class);
                        if(login.getPassword().equals(password)){
                            hasil = true;
                        }else{
                            hasil = false;
                        }
                    }
                } else{
                    hasil = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                hasil = false;
            }
        });

        return hasil;
    }


    private void saveCredentials(String username,String password,String id,String level) {
        SharedPreferences handler = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = handler.edit();

        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("id",id);
        editor.putString("level",level);

        editor.apply();
    }
}
