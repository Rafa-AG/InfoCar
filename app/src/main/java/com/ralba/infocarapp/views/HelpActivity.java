package com.ralba.infocarapp.views;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.ralba.infocarapp.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebView helpWebView = findViewById(R.id.HelpWebView);
        helpWebView.getSettings().setJavaScriptEnabled(true);
        String wv = "";

        wv = getIntent().getStringExtra("wv");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            if(!wv.equals("")){
                if(wv.equals("list")){
                    helpWebView.loadUrl("");
                }else if(wv.equals("form")){
                    helpWebView.loadUrl("");
                }else if(wv.equals("search")){
                    helpWebView.loadUrl("");
                }
            }else{
                finish();
            }
        }else{
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG)
                    .show();
            finish();
        }

    }
}