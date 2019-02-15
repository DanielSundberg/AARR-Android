package com.danielsundberg.yarr;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class YARRWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView  view, String  url){
        try {
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                //
                // We get this event for some elements on the pages we load.
                // Therefore we just block anything that is not a real link.
                //
                // Displaying error message can wait until later...
                //
                //Toast toast = Toast.makeText(
                //        view.getContext(),
                //        "Can't go to url " + url + "\n" +
                //                "Url must start with http://",
                //        Toast.LENGTH_SHORT);
                //toast.show();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
