package com.example.ts.neteasytest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ts.neteasytest.R;

/**
 * Created by ts on 20-1-14.
 */

public class AllBaseFragment extends Fragment {
    private TextView mTextView;
    private View mRootView;
    private  String mText = "";

    public AllBaseFragment(String mText) {
        this.mText = mText;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.gyb_fragment_blank,container , false);
        mTextView = (TextView) mRootView.findViewById(R.id.id_blankFragment_textview);
        mTextView.setText(mText);
        return mRootView;//返回view

    }
}
