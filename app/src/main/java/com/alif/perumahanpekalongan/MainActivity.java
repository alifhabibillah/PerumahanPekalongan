package com.alif.perumahanpekalongan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        ImageButton img_btn_cari = (ImageButton) findViewById(R.id.img_btn_cari);
        ImageButton img_btn_info = (ImageButton) findViewById(R.id.img_btn_info);

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
    }
}
