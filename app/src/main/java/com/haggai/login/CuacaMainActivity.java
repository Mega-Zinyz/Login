package com.haggai.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class CuacaMainActivity extends AppCompatActivity {

    private RecyclerView _recyclerView2;
    private SwipeRefreshLayout _swipeRefreshLayout2;
    private Button _buttonViewCityInfo;
    private CuacaRootModel _rootModel; // Define _rootModel here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuaca_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeRefreshLayout2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _recyclerView2 = findViewById(R.id.recyclerView2);
        _swipeRefreshLayout2 = findViewById(R.id.swipeRefreshLayout2);

        // Initialize RecyclerView and SwipeRefreshLayout
        initRecyclerView2();
        initSwipeRefreshLayout();
        initButtonViewCityInfo(); // Ensure _rootModel is initialized before calling this
    }

    private void initSwipeRefreshLayout() {
        _swipeRefreshLayout2.setOnRefreshListener(() -> initRecyclerView2());
    }

    private void initRecyclerView2() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=1630789&appid=dd3c3b81f05c0abd651382da1292d6ea";
        AsyncHttpClient ahc = new AsyncHttpClient();

        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("*hgh*", new String(responseBody));

                // Parse JSON response and populate _rootModel
                Gson gson = new Gson();
                _rootModel = gson.fromJson(new String(responseBody), CuacaRootModel.class);

                // Update city information button text
                initCityInfo();

                // Initialize RecyclerView with data
                RecyclerView.LayoutManager lm = new LinearLayoutManager(CuacaMainActivity.this);
                CuacaAdapter ca = new CuacaAdapter(CuacaMainActivity.this, _rootModel);
                _recyclerView2.setLayoutManager(lm);
                _recyclerView2.setAdapter(ca);

                _swipeRefreshLayout2.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                _swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }

    private void initButtonViewCityInfo() {
        _buttonViewCityInfo = findViewById(R.id.buttonView_cityinfo);

        _buttonViewCityInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_rootModel != null) {
                    CityModel cm = _rootModel.getCityModel();
                    CoordModel com = cm.getCoordModel();
                    double latitude = com.getLat();
                    double longitude = com.getLon();

                    Bundle param = new Bundle();
                    param.putDouble("lat", latitude);
                    param.putDouble("lon", longitude);

                    Intent intent = new Intent(CuacaMainActivity.this, GPSActivity.class);
                    intent.putExtra("param", param);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Data not loaded yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initCityInfo() {
        if (_rootModel != null) {
            CityModel cm = _rootModel.getCityModel();
            long sunrise = cm.getSunrise();
            long sunset = cm.getSunset();
            String cityName = cm.getName();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String sunriseTime = sdf.format(new Date(sunrise * 1000));
            String sunsetTime = sdf.format(new Date(sunset * 1000));

            String cityInfo = "Kota: " + cityName + "\n" +
                    "Matahari Terbit: " + sunriseTime + " (Lokal)\n" +
                    "Matahari Terbenam: " + sunsetTime + " (Lokal)";

            _buttonViewCityInfo.setText(cityInfo);
        }
    }
}
