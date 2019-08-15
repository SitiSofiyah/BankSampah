package com.example.intel.fireapp.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {
    private EditText username, password;
    RelativeLayout loginForm;
    ProgressBar prolog;
    FirebaseAuth auth;
    String verificationid, kode;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

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
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Snackbar.make(findViewById(R.id.login), "Isikan username dan password anda !",Snackbar.LENGTH_LONG).show();
        }else{
            prolog.setVisibility(View.VISIBLE);
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            String telp = username.getText().toString();
            final String pass = password.getText().toString();
            final Query query = databaseReference.child("users").orderByChild("username").equalTo(telp);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                            User login = userSnapshot.getValue(User.class);
                            if(login.getPassword().equals(pass)){
                                prolog.setVisibility(View.INVISIBLE);
                                level = login.getLevel().toString();
                                sendVerification(login.getTelp().toString().trim());
                                final EditText input = new EditText(login.this);
                                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                final AlertDialog builder = new AlertDialog.Builder(login.this)
                                .setTitle("Masukkan kode verifikasi !")
                                .setView(input)
                                .setPositiveButton("Verifikasi", null)
                                .setNegativeButton("Batal", null)
                                .show();
                                Button positive = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                                Button negative = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                                positive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        kode = input.getText().toString();
                                        verifyCode(kode);
                                        builder.dismiss();
                                    }
                                });
                                negative.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        builder.cancel();
                                    }
                                });

                            }else{
                                prolog.setVisibility(View.INVISIBLE);
                                Toast.makeText(login.this, "Akun tidak tersedia!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        prolog.setVisibility(View.INVISIBLE);
                        Toast.makeText(login.this, "Akun tidak tersedia!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void sendVerification(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Verification Code is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            if(level.equals("Pengepul Kecil")){
                                SaveSharedPreference.setLoggedInPK(getApplicationContext(), true);
                                SaveSharedPreference.setId(getApplicationContext(),id);
                                Intent pk = new Intent(login.this, HomePK.class);
                                startActivity(pk);
                                finish();
                            }else if(level.equals("Tukang Rombeng")){
                                SaveSharedPreference.setLoggedInTR(getApplicationContext(), true);
                                SaveSharedPreference.setId(getApplicationContext(),id);
                                Intent tr = new Intent(login.this, Home_tr.class);
                                startActivity(tr);
                                finish();
                            }else{
                                SaveSharedPreference.setLoggedInAnggota(getApplicationContext(), true);
                                SaveSharedPreference.setId(getApplicationContext(),id);
                                Intent anggota = new Intent(login.this, Home_Anggota.class);
                                startActivity(anggota);
                                finish();
                            }
                        }else{
                            Toast.makeText(login.this,"Kode verifikasi keliru",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}
