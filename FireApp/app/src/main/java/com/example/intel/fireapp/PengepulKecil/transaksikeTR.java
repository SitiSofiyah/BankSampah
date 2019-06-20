package com.example.intel.fireapp.PengepulKecil;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intel.fireapp.Account.Bantuan;
import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.Model.TransaksiKeTR;
import com.example.intel.fireapp.Model.User;
import com.example.intel.fireapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class transaksikeTR extends AppCompatActivity {

    private Button order;
    private EditText plastik, kertas, logam, kaca, lainnya, total;
    private DatabaseReference menDatabase;
    int totalSampah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksike_tr);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Jual Sampah");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plastik = (EditText) findViewById(R.id.plastik);
        kertas = (EditText) findViewById(R.id.kertas_pk);
        logam = (EditText) findViewById(R.id.logam_pk);
        kaca = (EditText) findViewById(R.id.kaca_pk);
        lainnya = (EditText) findViewById(R.id.lainnya_pk);
        order = (Button) findViewById(R.id.order);



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(plastik.getText().toString().isEmpty()||kertas.getText().toString().isEmpty()||
                        logam.getText().toString().isEmpty()||kaca.getText().toString().isEmpty()||lainnya.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(transaksikeTR.this);
                    builder.setMessage("Lengkapi data diri anda !");

                    // add a button
                    builder.setPositiveButton("OK",null);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else{
                    CreateTransaksiTR();
                    AlertDialog.Builder builder = new AlertDialog.Builder(transaksikeTR.this);
                    builder.setTitle("Jual Sampah");
                    builder.setMessage("Sampah anda dijual sebesar "+totalSampah+" Kg");

                    // add a button
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(transaksikeTR.this, HomePK.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.akun:
                // User chose the "Settings" item, show the app settings UI...
                Intent intents = new Intent(transaksikeTR.this, db_ReadAkun.class);
                startActivity(intents);
                return true;

            case R.id.help:
                Intent intentt = new Intent(transaksikeTR.this, Bantuan.class);
                startActivity(intentt);
                return true;

            case R.id.out:
                SaveSharedPreference.setLoggedInPK(getApplicationContext(), false);
                SaveSharedPreference.setId(getApplicationContext(), null);
                Intent intent = new Intent(transaksikeTR.this, login.class);
                startActivity(intent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void CreateTransaksiTR() {

        final String plastikss = plastik.getText().toString();
        final String kertass = kertas.getText().toString();
        final String logams = logam.getText().toString();
        final String kacas = kaca.getText().toString();
        final String lainnyas = lainnya.getText().toString();
        int total = Integer.parseInt(plastikss)+Integer.parseInt(kertass)+Integer.parseInt(logams)+Integer.parseInt(kacas)+Integer.parseInt(lainnyas) ;
        totalSampah=total;
        menDatabase = FirebaseDatabase.getInstance().getReference("transaksiTR");

        String id_ordersampah = menDatabase.push().getKey();

        java.text.SimpleDateFormat fomat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String curdate =  fomat.format(date).toString();

        TransaksiKeTR transaksiKeTR = new TransaksiKeTR(id_ordersampah, SaveSharedPreference.getId(getApplicationContext()),"", plastikss,kertass, logams, kacas, lainnyas, String.valueOf(total),curdate,"belum");

        menDatabase.child(id_ordersampah).setValue(transaksiKeTR);
    }

}
