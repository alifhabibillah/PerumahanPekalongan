package com.alif.perumahanpekalongan;

import com.alif.perumahanpekalongan.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alif on 15/11/2016.
 */

public class BaratFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_barat, container, false);

        return rootView;
    }
}
