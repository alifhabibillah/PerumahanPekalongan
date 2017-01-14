package com.alif.perumahanpekalongan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alif.perumahanpekalongan.adapter.CardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alif on 12/12/2016.
 */

public class CariActivity extends AppCompatActivity implements CardAdapter.ClickListener {

    private CardAdapter adapter;
    private List<MyData> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_cari);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        load_data();

        adapter = new CardAdapter(this, dataList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load_data() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/cari.php")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        MyData data = new MyData(object.getString("kdperum"), object.getString("nmperum"),
                                object.getString("kelurahan"), object.getString("kecamatan"), object.getString("detail"), object.getString("foto"),
                                object.getString("username"), object.getString("tanggal"));

                        dataList.add(data);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //Cek konektivitas ke server
                    CariActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CariActivity.this, "Koneksi Terputus. Silakan Cek Koneksi Internet Anda.", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Collections.sort(dataList, new Comparator<MyData>() {
                    @Override
                    public int compare(MyData lhs, MyData rhs) {
                        return lhs.getNmperum().compareTo(rhs.getNmperum());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();
    }

    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(this, SpinnerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Detail Cari", dataList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
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
