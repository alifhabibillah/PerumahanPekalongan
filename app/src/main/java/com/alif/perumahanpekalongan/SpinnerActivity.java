package com.alif.perumahanpekalongan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alif on 17/12/2016.
 */

public class SpinnerActivity extends AppCompatActivity {

    public TextView perum_pilih;
    private Spinner spinnerJalan, spinnerBlok, spinnerRumah;
    List<String> labelJalan = new ArrayList<String>();
    List<String> labelBlok = new ArrayList<String>();
    List<String> labelRumah = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        final MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail Cari");

        perum_pilih = (TextView) findViewById(R.id.perum_pilih);
        perum_pilih.setText(myData.getNmperum());

        spinnerJalan = (Spinner) findViewById(R.id.spin_jalan);
        spinnerBlok = (Spinner) findViewById(R.id.spin_blok);
        spinnerRumah = (Spinner) findViewById(R.id.spin_rumah);

        load_jalan(myData.getKdperum());

        Button btn_cari = (Button) findViewById(R.id.btn_cari);

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kdPerum = myData.getKdperum();
                String nmJalan = spinnerJalan.getSelectedItem().toString();
                String bLok    = spinnerBlok.getSelectedItem().toString();
                String noRumah = spinnerRumah.getSelectedItem().toString();
                Intent map_intent = new Intent(SpinnerActivity.this, MapsActivity.class);
                map_intent.putExtra("Kdperum", kdPerum);
                map_intent.putExtra("Nmjalan", nmJalan);
                map_intent.putExtra("Blok", bLok);
                map_intent.putExtra("Norumah", noRumah);
                startActivity(map_intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load_jalan(final String kdperum) {
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/spin_jalan.php?kdperum="+kdperum)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        if (!labelJalan.contains(object.getString("nmjalan"))) {
                            Log.i("nmjalan", object.getString("nmjalan"));
                            labelJalan.add(object.getString("nmjalan"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                final ArrayAdapter<String> jalanAdapter = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, labelJalan);
                spinnerJalan.setAdapter(jalanAdapter);
                spinnerJalan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail Cari");

                        labelBlok.clear();
                        load_blok(myData.getKdperum(),parent.getSelectedItem().toString());
                        jalanAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };

        task.execute(kdperum);
    }
    private void load_blok(final String kdperum, final String nmjalan) {
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/spin_blok.php?kdperum="+kdperum+"&nmjalan="+nmjalan)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        if (!labelBlok.contains(object.getString("blok"))) {
                            Log.i("blok", object.getString("blok"));
                            labelBlok.add(object.getString("blok"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                final ArrayAdapter<String> blokAdapter = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, labelBlok);
                spinnerBlok.setAdapter(blokAdapter);
                spinnerBlok.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail Cari");

                        labelRumah.clear();
                        load_rumah(myData.getKdperum(),spinnerJalan.getSelectedItem().toString(),parent.getSelectedItem().toString());
                        blokAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };

        task.execute(kdperum,nmjalan);
    }
    private void load_rumah(final String kdperum, final String nmjalan, final String blok) {
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/spin_rumah.php?kdperum="+kdperum+"&nmjalan="+nmjalan+"&blok="+blok)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        if (!labelRumah.contains(object.getString("norumah"))) {
                            Log.i("norumah", object.getString("norumah"));
                            labelRumah.add(object.getString("norumah"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter<String> rumahAdapter = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, labelRumah);
                spinnerRumah.setAdapter(rumahAdapter);
            }
        };

        task.execute(kdperum,nmjalan,blok);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
