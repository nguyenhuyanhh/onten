package com.onten.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhenting on 29/10/2015.
 */
public class AssemblerDirectivesActivity extends Fragment {

    WebView myWebView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.determinants, container, false);
        // Retrieve UI elements
        myWebView = (WebView) view.findViewById(R.id.webView1);

        // Initialize the WebView
        myWebView.getSettings().setJavaScriptEnabled(true);
        //myWebView.loadUrl("http://onten.eee.ntu.edu.sg/node/11");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the URLs inside the WebView, not in the external web browser
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                myWebView.loadUrl("javascript:(function() { " +
                        //hide the main menu and courses
                        "document.getElementsByClassName('region region-sidebar-first sidebar')[0].style.display='none'; " +
                        //hide the footer
                        "document.getElementsByTagName('footer')[0].style.display='none'; " +
                        //hide the survey feedback
                        "document.getElementsByClassName('webform-grid webform-grid-5 sticky-enabled')[0].style.display='none'; " +
                        //align and justify the text
                        "document.getElementsByClassName('node-content')[0].style.textAlign = 'justify'; " +
                        //spaces between the text height
                        "document.getElementsByClassName('node-content')[0].style.lineHeight = '200%'; " +
                        "})()");
            }
        });

        if (savedInstanceState == null) {
            // Load a page
            myWebView.loadUrl("http://onteneee.ddns.net/node/43");

            //myWebView.loadUrl("http://onten.eee.ntu.edu.sg/node/11");
        }
        return view;
    }

}
