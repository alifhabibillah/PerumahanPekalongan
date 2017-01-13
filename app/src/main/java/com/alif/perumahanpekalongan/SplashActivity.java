package com.alif.perumahanpekalongan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Alif on 10/01/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private static int splashInterval = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main_intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(main_intent);

                this.finish();
            }

            private void finish() {
                // Cek ketersediaan koneksi internet
                ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = con.getActiveNetworkInfo();
                if (info == null) {
                    Toast.makeText(SplashActivity.this, "Koneksi Tidak Tersedia. Pastikan WiFi atau Data Seluler Aktif.", Toast.LENGTH_LONG).show();
                }
            }
        }, splashInterval);
    }
}
