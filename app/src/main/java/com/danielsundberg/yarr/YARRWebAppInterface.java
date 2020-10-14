package com.danielsundberg.yarr;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class YARRWebAppInterface {
    private WebView mWebView;
    private Context mContext;

    /** Instantiate the interface and set the context */
    YARRWebAppInterface(Context c, WebView webView) {
        mContext = c;
        mWebView = webView;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void shareUrl(String title, String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, url);

        mContext.startActivity(Intent.createChooser(share, "Share blog post!"));
    }

    public void StartSession() {

        String jsCommand = String.format("javascript:window.ContainerAppCallbacks.startSession(\"%s\", \"%s\")",
                BuildConfig.aarrstat_api_url, BuildConfig.aarrstat_api_key);
        mWebView.loadUrl(jsCommand);
    }

    public void EndSession() {
        String jsCommand = String.format("javascript:window.ContainerAppCallbacks.endSession(\"%s\", \"%s\")",
                BuildConfig.aarrstat_api_url, BuildConfig.aarrstat_api_key);
        mWebView.loadUrl(jsCommand);
    }
}
