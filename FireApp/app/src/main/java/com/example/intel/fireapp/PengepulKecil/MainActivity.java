package com.example.intel.fireapp.PengepulKecil;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.intel.fireapp.Account.Utils.SaveSharedPreference;
import com.example.intel.fireapp.Account.login;
import com.example.intel.fireapp.R;
import com.example.intel.fireapp.otpActivity;


public class MainActivity extends AppCompatActivity {

    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone=(EditText)findViewById(R.id.phone);
    }

    public void regis(View view) {
        String number = phone.getText().toString().trim();
        if(number.isEmpty()||number.length()<10){
            phone.setError("Nomor tidak valid !");
            phone.requestFocus();
            return;
        }

        String phonenumber = "+62"+number;

        Intent intent = new Intent(MainActivity.this,otpActivity.class);
        intent.putExtra("phone",phonenumber);
        startActivity(intent);
    }
}
