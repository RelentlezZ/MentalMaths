package org.relentlezz.mentalmaths.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class ExtraFunActivity extends Activity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = new WebView(ExtraFunActivity.this);
        mWebView.loadUrl("http://www.physik-lk.widukindgymnasium.de");
        setContentView(mWebView);

    }



}
