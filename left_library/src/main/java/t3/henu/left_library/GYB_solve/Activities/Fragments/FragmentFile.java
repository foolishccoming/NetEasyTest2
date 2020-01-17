package t3.henu.left_library.GYB_solve.Activities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import android.webkit.WebView;

import t3.henu.left_library.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFile extends Fragment {

    private View rootView;
    private WebView webView;

    public FragmentFile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_file, container, false);
        // Inflate the layout for this fragment
        initView();

        return rootView;
    }

    private void initView() {
        webView = (WebView) rootView.findViewById(R.id.id_webview);
        webView.loadUrl("http://news.163.com/");
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
    }

}
