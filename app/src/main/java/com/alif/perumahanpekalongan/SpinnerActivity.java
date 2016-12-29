package com.alif.perumahanpekalongan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alif on 17/12/2016.
 */

public class SpinnerActivity extends AppCompatActivity {

    public TextView perum_pilih;
    private Spinner spinnerJalan, spinnerBlok, spinnerRumah;
    private List<MyKoord> myKoordList;
    List<String> labelJalan = new ArrayList<String>();
    List<String> labelBlok = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail Cari");

        perum_pilih = (TextView) findViewById(R.id.perum_pilih);
        perum_pilih.setText(myData.getNmperum());

        myKoordList = new ArrayList<>();
        load_jalan(myData.getKdperum());

        spinnerJalan = (Spinner) findViewById(R.id.spin_jalan);
        spinnerBlok = (Spinner) findViewById(R.id.spin_blok);
        spinnerRumah = (Spinner) findViewById(R.id.spin_rumah);

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

                        MyKoord data = new MyKoord(object.getString("kdkoord"), object.getString("kdperum"),
                                object.getString("nmjalan"), object.getString("blok"), object.getString("norumah"), object.getDouble("latitude"),
                                object.getDouble("longitude"));

                        myKoordList.add(data);

                        labelJalan.add(myKoordList.get(i).getNmjalan());
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
                //super.onPostExecute(aVoid);
                final ArrayAdapter<String> jalanAdapter = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, labelJalan);
                spinnerJalan.setAdapter(jalanAdapter);
                spinnerJalan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail Cari");
                        String selectedValue = parent.getSelectedItem().toString();

                        labelBlok.clear();
                        load_blok(myData.getKdperum(),selectedValue);
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

                        MyKoord data = new MyKoord(object.getString("kdkoord"), object.getString("kdperum"),
                                object.getString("nmjalan"), object.getString("blok"), object.getString("norumah"), object.getDouble("latitude"),
                                object.getDouble("longitude"));

                        myKoordList.add(data);

                        labelBlok.add(myKoordList.get(i).getBlok());
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
                //super.onPostExecute(aVoid);
                ArrayAdapter<String> blokAdapter = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, labelBlok);
                spinnerBlok.setAdapter(blokAdapter);
                blokAdapter.notifyDataSetChanged();
            }
        };

        task.execute(kdperum,nmjalan);
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
