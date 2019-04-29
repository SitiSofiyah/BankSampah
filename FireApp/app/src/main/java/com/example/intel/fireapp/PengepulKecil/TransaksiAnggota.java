package com.example.intel.fireapp.PengepulKecil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.intel.fireapp.R;

public class TransaksiAnggota extends AppCompatActivity {

    public EditText harga, ket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_anggota);

        harga = (EditText) findViewById(R.id.harga);
        ket = (EditText) findViewById(R.id.keterangan);
    }

    public void addTransaksi(View view) {
        
    }
}
