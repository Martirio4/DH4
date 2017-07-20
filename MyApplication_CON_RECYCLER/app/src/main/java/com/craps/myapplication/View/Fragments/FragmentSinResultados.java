package com.craps.myapplication.View.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craps.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSinResultados extends Fragment {
    public FragmentSinResultados() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sin_coincidencias, container, false);
    }
}
