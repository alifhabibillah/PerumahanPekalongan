package com.alif.perumahanpekalongan;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.alif.perumahanpekalongan.adapter.InfoFragmentPagerAdapter;
import com.alif.perumahanpekalongan.crud.config.Config;
import com.alif.perumahanpekalongan.crud.config.Http;

/**
 * Created by Alif on 15/11/2016.
 */

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        InfoFragmentPagerAdapter pagerAdapter = new InfoFragmentPagerAdapter(getSupportFragmentManager(), InfoActivity.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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
