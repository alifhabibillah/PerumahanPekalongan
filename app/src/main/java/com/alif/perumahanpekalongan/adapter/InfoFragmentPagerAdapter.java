package com.alif.perumahanpekalongan.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alif.perumahanpekalongan.BaratFragment;
import com.alif.perumahanpekalongan.SelatanFragment;
import com.alif.perumahanpekalongan.TimurFragment;
import com.alif.perumahanpekalongan.UtaraFragment;

/**
 * Created by Alif Habibillah.
 */

public class InfoFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] {"Barat", "Selatan", "Timur", "Utara"};
    private Context context;
    public InfoFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        // get item count
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BaratFragment();
            case 1:
                return new SelatanFragment();
            case 2:
                return new TimurFragment();
            case 3:
                return new UtaraFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
