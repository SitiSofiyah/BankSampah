package com.example.intel.fireapp.Account;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Anggota.Home_Anggota;
import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.PengepulKecil.HomePK;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.TukangRombeng.Home_tr;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.text.TextUtils.isEmpty;

public class Update extends AppCompatActivity {

    private EditText ed_Nama, ed_Alamat, ed_Telp, ed_Password;
    private Button bt_update;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ed_Nama = (EditText) findViewById(R.id.ed_nama);
        ed_Alamat = (EditText) findViewById(R.id.ed_alamat);
        ed_Telp = (EditText) findViewById(R.id.ed_telp);
        ed_Password = (EditText) findViewById(R.id.ed_password);
        bt_update = (Button) findViewById(R.id.update);


        mDatabase = FirebaseDatabase.getInstance().getInstance().getReference();

        final Query query = mDatabase.child("users").orderByChild("id").equalTo(SaveSharedPreference.getId(getApplicationContext()));


        final User user = (User) getIntent().getSerializableExtra("data");
        if (user != null) {
            ed_Nama.setText(user.getNama());
            ed_Password.setText(user.getPassword());
            ed_Alamat.setText(user.getAlamat());
            ed_Telp.setText(user.getTelp());

            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.setNama(ed_Nama.getText().toString());
                    user.setPassword(ed_Password.getText().toString());
                    user.setAlamat(ed_Alamat.getText().toString());
                    user.setTelp(ed_Telp.getText().toString());

                    updateAkun(user);
                }
            });
            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEmpty(ed_Nama.getText().toString()) && !isEmpty(ed_Password.getText().toString()) && !isEmpty(ed_Alamat.getText().toString()) && !isEmpty(ed_Telp.getText().toString()))
                        ;
//                        submitUsers(new users(ed_Nama.getText().toString(), ed_Password.getText().toString(), ed_Alamat.getText().toString(), ed_Telp.getText().toString()));
                    else
                        Snackbar.make(findViewById(R.id.update), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            ed_Nama.getWindowToken(), 0);
                }
            });
        }
    }

        private void updateAkun(User user) {
            /**
             * Baris kode yang digunakan untuk mengupdate data barang
             * yang sudah dimasukkan di Firebase Realtime Database
             */
            mDatabase.child("users") //akses parent index, ibaratnya seperti nama tabel
                    .child(user.getId()) //select barang berdasarkan key
                    .setValue(user) //set value barang yang baru
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            /**
                             * Baris kode yang akan dipanggil apabila proses update barang sukses
                             */
                            Snackbar.make(findViewById(R.id.update), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                        }
                    });
        }
        private void submitBarang(User user) {
            /**
             * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
             * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
             * ketika data berhasil ditambahkan
             */
            mDatabase.child("users").push().setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    ed_Nama.setText("");
                    ed_Password.setText("");
                    ed_Alamat.setText("");
                    ed_Telp.setText("");
                    Snackbar.make(findViewById(R.id.update), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                }
            });
        }



    }



