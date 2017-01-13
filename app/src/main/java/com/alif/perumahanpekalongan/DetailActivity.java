package com.alif.perumahanpekalongan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Alif on 10/12/2016.
 */

public class DetailActivity extends AppCompatActivity {
    public ImageView detailFoto;
    public TextView detailPerum, detailDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        MyData myData = (MyData)getIntent().getExtras().getSerializable("Detail");

        detailFoto = (ImageView) findViewById(R.id.foto_perum);
        detailPerum = (TextView) findViewById(R.id.nm_perum);
        detailDetail = (TextView) findViewById(R.id.detail_perum);

        Picasso.with(this).load(myData.getFoto()).into(detailFoto);
        detailPerum.setText(myData.getNmperum());
        detailDetail.setText(myData.getDetail());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
