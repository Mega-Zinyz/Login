package com.haggai.login;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GPSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gps), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        WebView webView = findViewById(R.id.wvMain);
        TextView textViewCoordinat = findViewById(R.id.textView_koordinat);

        Bundle param = getIntent().getBundleExtra("param");
        double latitude = param.getDouble("lat");
        double longitude = param.getDouble("lon");

        // Display coordinates in TextView
        textViewCoordinat.setText(latitude + " x " + longitude);

        // Construct Google Maps URL
        String url = "https://www.google.com/maps" +
                "?q=" + latitude + "," + longitude +
                "&ll=" + latitude + "," + longitude +
                "&z=10";

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if required by Google Maps
        webView.setWebViewClient(new WebViewClient());

        // Load Google Maps URL in WebView
        webView.loadUrl(url);
    }
}
