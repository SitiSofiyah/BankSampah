package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class transaksikeTR extends AppCompatActivity {
    private Button order;
    private EditText plastik, kertas, logam, kaca, lainnya, total;
    private DatabaseReference menDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksike_tr);

        plastik = (EditText) findViewById(R.id.plastik);
        kertas = (EditText) findViewById(R.id.kertas_pk);
        logam = (EditText) findViewById(R.id.logam_pk);
        kaca = (EditText) findViewById(R.id.kaca_pk);
        lainnya = (EditText) findViewById(R.id.lainnya_pk);
        total = (EditText) findViewById(R.id.total);
        order = (Button) findViewById(R.id.order);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String TransaksiTR = plastik.getText().toString();
                final Query query = databaseReference.child("transaksiTR").orderByChild("plastik").equalTo(TransaksiTR);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CreateTransaksiTR();
                        Toast.makeText(transaksikeTR.this, "Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(transaksikeTR.this, HomePK.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void CreateTransaksiTR() {

        final String plastik = plastik.getText().toString();
        final String kertas = kertas.getText().toString();
        final String logam  = logam.getText().toString();
        final String kaca = kaca.getText().toString();
        final String lainnya = lainnya.getSelectedItem().toString();
        final String total = total.getText().toString();

        menDatabase = FirebaseDatabase.getInstance().getReference("transaksiTR");

        String id_ordersampah = menDatabase.push().getKey();



        User user = new User(id, name,address, telpon, jk, level, pass, "no");

        meDatabase.child(id).setValue(user);
    }
}
