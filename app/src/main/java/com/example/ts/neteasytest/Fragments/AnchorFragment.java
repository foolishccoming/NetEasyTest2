package com.example.ts.neteasytest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ts.neteasytest.R;

/**
 * Created by ts on 20-1-17.
 */

public class AnchorFragment extends Fragment {
    public AnchorFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zhubo,container,false);
    }
}
