package t3.henu.left_library.CHY_solve;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import t3.henu.left_library.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentZhubo extends Fragment {
    private View rootView;

    public FragmentZhubo() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chy_fragment_zhubo, container, false);
        return rootView;
    }
}
