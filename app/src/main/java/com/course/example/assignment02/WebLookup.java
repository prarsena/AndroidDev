package com.course.example.assignment02;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/* The second activity of the application.
   This activity provides the WebView functionality. */

public class WebLookup extends Activity {
    private EditText urlText;
    private Button goButton;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        // Get a handle to all user interface elements
        urlText = (EditText) findViewById(R.id.url_field);
        goButton = (Button) findViewById(R.id.go_button);
        webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);

        //ensure clicking links keep opening in the widget rather than opening the browser.
        webView.setWebViewClient(new WebViewClient());

        // Set button to open browser
        goButton.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                /* Parse the entered text in the urlText field.
                *  If it doesn't start with https://, that is prefixed to the request. */
                String url = urlText.getText().toString();
                if (!(url.startsWith("https://"))){
                    if (url.startsWith("www")){
                        url = "https://" + url;
                    }
                    else {
                        url = "https://www." + url;
                    }
                }
                Log.i("Website", url);
                webView.loadUrl(url);
            }
        });

        //set listener on EditText. Either click the GO button, or press ENTER key.
        urlText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    webView.loadUrl(urlText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    //the back key navigates back to the previous web page
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        setResult(Activity.RESULT_OK);
        return super.onKeyDown(keyCode, event);
    }


}
