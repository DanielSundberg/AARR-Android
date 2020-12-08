package com.danielsundberg.yarr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView mWebView;
    private YARRWebAppInterface mYarrWebAppInterface;
    private boolean mJSAppLoaded = false;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView.setWebContentsDebuggingEnabled(true);

        this.requestWindowFeature((int)Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        mYarrWebAppInterface = new YARRWebAppInterface(this, mWebView);
        mWebView.addJavascriptInterface(mYarrWebAppInterface, "YARRAndroid");

        mWebView.setWebViewClient(new YARRWebViewClient());
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));

        mWebView.loadUrl("file:///android_asset/www/index.html");
    }

    // Prevent the back-button from closing the app
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    protected void onResume() {
        System.out.println("onResume");
        if (mJSAppLoaded) {
            mYarrWebAppInterface.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause");
        if (mJSAppLoaded) {
            mYarrWebAppInterface.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy");
        mJSAppLoaded = false;
        super.onDestroy();
    }

    private class YARRWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                // For urls that start with file:// or something other then http(s) we do nothing
            } else {
                Intent intent = new Intent(Intent.ACTION_DEFAULT, Uri.parse(url));
                startActivity(intent);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            mJSAppLoaded = true;
            mYarrWebAppInterface.initCallbacks();
            mYarrWebAppInterface.onResume();
        }
    }
}
