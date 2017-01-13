package com.alif.perumahanpekalongan;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude = 0.0;
    Double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();

        String kdPerum = bundle.getString("Kdperum");
        String nmJalan = bundle.getString("Nmjalan");
        String bLok    = bundle.getString("Blok");
        String noRumah = bundle.getString("Norumah");

        load_koordinat(kdPerum,nmJalan,bLok,noRumah);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
            }
        }, 5000);

    }

    private void load_koordinat(final String kdperum, final String nmjalan, final String blok, final String norumah) {
        final AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.43.192/perumahan/koordinat.php?kdperum="+kdperum+"&nmjalan="+nmjalan+"&blok="+blok+"&norumah="+norumah)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        latitude = object.getDouble("latitude");
                        longitude = object.getDouble("longitude");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MapsActivity.this, "Koneksi Terputus. Silakan Cek Koneksi Internet Anda.", Toast.LENGTH_LONG).show();
                        }
                    });
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
        task.execute(kdperum,nmjalan,blok,norumah);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Rumah and animate the camera
        LatLng rumah = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(rumah).title("Lokasi Rumah"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rumah, 17), 2000, null);
        mMap.setMapType(googleMap.MAP_TYPE_HYBRID);
    }
}
