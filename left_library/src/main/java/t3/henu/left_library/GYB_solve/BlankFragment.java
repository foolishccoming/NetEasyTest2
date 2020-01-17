package t3.henu.left_library.GYB_solve;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import t3.henu.left_library.R;



public class BlankFragment extends Fragment {

    private TextView textView;
    private View rootView;
    private String text="";

    public BlankFragment(String text) {
        this.text = text;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gyb_fragment_blank, container, false);
        textView= (TextView) rootView.findViewById(R.id.id_blankFragment_textview);
        textView.setText(text);
        return rootView;
    }
}

