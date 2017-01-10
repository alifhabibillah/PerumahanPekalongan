package com.alif.perumahanpekalongan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        ImageButton img_btn_cari = (ImageButton) findViewById(R.id.img_btn_cari);
        ImageButton img_btn_info = (ImageButton) findViewById(R.id.img_btn_info);
        ImageButton img_btn_tentang = (ImageButton) findViewById(R.id.img_btn_tentang);

        img_btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cari_intent = new Intent(MainActivity.this, CariActivity.class);
                startActivity(cari_intent);
            }
        });
        img_btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(info_intent);
            }
        });
        img_btn_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tentang_intent = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(tentang_intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan kembali sekali lagi untuk keluar", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
