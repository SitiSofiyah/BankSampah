package com.example.intel.fireapp.Account;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.Tawaran;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.example.intel.fireapp.otpActivity;
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

public class register extends AppCompatActivity {

     private Button regis;
     private Spinner levell;
     private EditText nama, telp, alamat, password;
     private DatabaseReference meDatabase;
    FirebaseAuth auth;
    String verificationid, kode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();

        // Get reference of widgets from XML layout
        nama = (EditText) findViewById(R.id.nama) ;
        telp = (EditText) findViewById(R.id.telp) ;
        alamat = (EditText) findViewById(R.id.alamat) ;
        password = (EditText) findViewById(R.id.password) ;
         levell = (Spinner) findViewById(R.id.level);
        regis = (Button) findViewById(R.id.register);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama.getText().toString().isEmpty()||telp.getText().toString().isEmpty()||alamat.getText().toString().isEmpty()||password.getText().toString().isEmpty())
                {
                    Toast.makeText(register.this, "Lengkapi data diri anda", Toast.LENGTH_LONG).show();
                }else{
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    String user = telp.getText().toString();
                    final Query query = databaseReference.child("users").orderByChild("telp").equalTo(user);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                                Toast.makeText(register.this, "No telp sudah terdaftar", Toast.LENGTH_LONG).show();
                        }else {
                            String phonenumber = "+62"+telp.getText().toString().trim();
                            sendVerification(phonenumber);
                            AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                            builder.setTitle("Masukkan kode verifikasi !");

                            final EditText input = new EditText(register.this);
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            builder.setView(input);
                            builder.setPositiveButton("Verifikasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    kode = input.getText().toString();
                                    verifyCode(kode);
                                }
                            });
                            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
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
            Toast.makeText(register.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            createAccount();
                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            if(levell.getSelectedItem().toString().equals("Pengepul Kecil")){
                                SaveSharedPreference.setLoggedInPK(getApplicationContext(), true);
                                SaveSharedPreference.setId(getApplicationContext(),id);
                                Intent pk = new Intent(register.this, HomePK.class);
                                startActivity(pk);
                                finish();
                            }else if(levell.getSelectedItem().toString().equals("Tukang Rombeng")){
                                SaveSharedPreference.setLoggedInTR(getApplicationContext(), true);
                                SaveSharedPreference.setId(getApplicationContext(),id);
                                Intent tr = new Intent(register.this, Home_tr.class);
                                startActivity(tr);
                                finish();
                            }else{
                                SaveSharedPreference.setLoggedInAnggota(getApplicationContext(), true);
                                SaveSharedPreference.setId(getApplicationContext(),id);
                                Intent anggota = new Intent(register.this, Home_Anggota.class);
                                startActivity(anggota);
                                finish();
                            }
                        }else{

                        }
                    }
                });
    }

    public void createAccount(){
        final String name = nama.getText().toString();
        final String address = alamat.getText().toString();
        final String telpon = "+62"+telp.getText().toString();
        final String pass = password.getText().toString();
        final String level = levell.getSelectedItem().toString();
        meDatabase = FirebaseDatabase.getInstance().getReference("users");
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user = new User(id, name,address, telpon, level, pass, "no");
        meDatabase.child(id).setValue(user);
    }
}




