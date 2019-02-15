package com.danielsundberg.yarr;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class YARRWebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    YARRWebAppInterface(Context c) {
        mContext = c;
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
}
