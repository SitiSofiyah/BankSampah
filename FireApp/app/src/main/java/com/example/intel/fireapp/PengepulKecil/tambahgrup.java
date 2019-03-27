package com.example.intel.fireapp.PengepulKecil;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.intel.fireapp.Model.TambahGrup;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.EmptyStackException;

import static android.text.TextUtils.isEmpty;

public class tambahgrup extends AppCompatActivity {
    private Button btTambahGrup;
    private EditText etTambahgrup;
    SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahgrup);

        //inisialisasi edit teks dan button
        btTambahGrup = (Button) findViewById(R.id.tambahgrupbutton);
        etTambahgrup = (EditText) findViewById(R.id.tambahgrup);



        btTambahGrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (!isEmpty(etTambahgrup.getText().toString()))
                    TambahGrup();
//                else
//                    Snackbar.make(findViewById(R.id.tambahgrupbutton), "Data barang tidak boleh kosong",
//                            Snackbar.LENGTH_LONG).show();
//
//                InputMethodManager imm = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(
//                        etTambahgrup.getWindowToken(), 0);
            }

        });
    }
    private boolean isEmpty(String s){
        //Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
    private void TambahGrup() {
        final String nama_grup = etTambahgrup.getText().toString();
        SharedPreferences handler = this.getPreferences(Context.MODE_PRIVATE);
        String id_user = handler.getString("id","");
        String id_grup = mDatabase.push().getKey();

        mDatabase = FirebaseDatabase.getInstance().getReference("Grup");

        TambahGrup tambahGrup = new TambahGrup(id_grup, nama_grup, id_user);

        mDatabase.child(id_grup).setValue(tambahGrup);
    }
}
