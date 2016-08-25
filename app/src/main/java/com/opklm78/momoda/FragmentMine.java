package com.opklm78.momoda;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

public class FragmentMine extends Fragment implements View.OnClickListener{
    Button ib = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ib = (Button) getActivity().findViewById(R.id.fm_ib_about);

    }

    @Override
    public void onClick(View v) {
        if(getActivity().findViewById(v.getId())==ib){
            WebView wv = new WebView(this.getContext());
            wv.loadUrl("file:///assets/licenses.html");
            wv.setWebViewClient(new WebViewClient());
        }
    }
}
