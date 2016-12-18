package com.alif.perumahanpekalongan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.alif.perumahanpekalongan.adapter.SpinnerAdapter;

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
    private SpinnerAdapter spinnerAdapter;
    private Spinner spinnerBlok;
    private List<MyKoord> myKoordList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail Cari");

        perum_pilih = (TextView) findViewById(R.id.perum_pilih);
        perum_pilih.setText(myData.getNmperum());;

        myKoordList = new ArrayList<>();
        load_data();

        spinnerAdapter = new SpinnerAdapter(this, myKoordList);
        spinnerBlok = (Spinner) findViewById(R.id.spin_blok);
        spinnerBlok.setVisibility(View.VISIBLE);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(SpinnerActivity.this, myKoordList);
        spinnerBlok.setAdapter(spinnerAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**private void populateSpinner() {
        spinnerBlok = (Spinner) findViewById(R.id.spin_blok);
        spinnerBlok.setVisibility(View.VISIBLE);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, myKoordList);

        spinnerBlok.setAdapter(spinnerAdapter);
    }**/

    private void load_data() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/koordinat.php")
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
            }
        };

        task.execute();
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
