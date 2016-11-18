package com.alif.perumahanpekalongan;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alif.perumahanpekalongan.adapter.CardAdapter;

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
 * Created by Alif on 15/11/2016.
 */

public class BaratFragment extends Fragment {

    private CardAdapter adapter;
    private List<MyData> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_barat, container, false);
        // menampilkan recyclerview yang ada pada file layout dengan id listBarat
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listBarat);
        // menset ukuran
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // menset layout manager dan menampilkan list dalam bentuk linearlayout
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();
        load_data("Pekalongan Selatan");

        adapter = new CardAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void load_data(final String kecamatan) {
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/read.php?kecamatan='"+kecamatan+"'")
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        MyData data = new MyData(object.getString("nmperum"),
                                object.getString("kelurahan"), object.getString("kecamatan"));

                        dataList.add(data);
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
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(kecamatan);
    }
}
