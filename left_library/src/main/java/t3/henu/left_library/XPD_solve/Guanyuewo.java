package t3.henu.left_library.XPD_solve;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import t3.henu.left_library.R;

public class Guanyuewo extends Fragment {
    private TextView xingbie, qianming;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_guanyuewo, container, false);
        xingbie = (TextView) view.findViewById(R.id.xingbie);
        qianming = (TextView) view.findViewById(R.id.qianming);

        Intent intent = getActivity().getIntent();
        try {
            if (intent != null) {
                String xingbie = intent.getStringExtra("xingbie");

            }
        } catch (Exception e) {

        }
        return view;

    }

}
