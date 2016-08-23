package com.opklm78.momoda;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.opklm78.momoda.dao.DBManager;
import com.opklm78.momoda.web.MyWebViewClient;
import com.opklm78.momoda.web.WebAppInterface;

public class FragmentNovel extends Fragment {
    private  WebView wv = null;
    private WebChromeClient wcc = null;
    private WebViewClient  wvc = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.fragment_novel, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(wv==null){
            initWV();
        }
    }

    private void initWV(){
        if(wv==null){
            wv = (WebView) getActivity().findViewById(R.id.wv_novel);
//            wv = new WebView(getContext());
        }
        //覆盖原回退效果
        wv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN&&(keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()){
                   wv.goBack();
                    return true;
                }
                return false;
            }
        });
        //支持js
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setDatabaseEnabled(true);
        wv.getSettings().setDatabasePath(DBManager.DB_PATH+"/"+DBManager.DB_NAME);
        wv.addJavascriptInterface(new WebAppInterface(getContext()),"Android");
        wv.loadUrl("http://www.biquge.la/");
//        wv.setWebChromeClient(wcc);
        Log.e("load url","begin");
        wv.setWebViewClient(wvc);
        Log.e("load url","finish");
    }
    private void initWebChromeClient(){
        if(wcc == null){
            wcc = new WebChromeClient();
        }
    }
    private void initWebViewClient(){
        if(wvc == null){
//            wvc = new MyWebViewClient();
            wvc = new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
//                    view.loadDataWithBaseURL(url, "", "text/html", "utf-8", "");
                    Log.e("webview init","overrider for ?");
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    view.loadUrl(url);
                    super.onPageStarted(view, url, favicon);
                }
            };
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
