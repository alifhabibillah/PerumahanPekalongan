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

        ImageButton btn_info = (ImageButton) findViewById(R.id.btn_info);

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(info_intent);
            }
        });
    }
}
