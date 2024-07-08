package com.haggai.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private Button _loginButton;
    private EditText _idEditText, _passwordEditText;
    private Intent _menuIntent;
    private String _id, _password, _url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        _loginButton = findViewById(R.id.loginButton);
        _idEditText = findViewById(R.id.idEditText);
        _passwordEditText = findViewById(R.id.passwordEditText);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _id = _idEditText.getText().toString();
                _password = _passwordEditText.getText().toString();
                _url = "https://stmikpontianak.net/011100862/login.php?id=" + _id + "&password=" + _password;

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(_url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String hasil = new String(responseBody);
                            JSONArray jsonArray = new JSONArray(hasil);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            int idCount = jsonObject.getInt("idCount");

                            if (idCount != 1) {
                                Toast.makeText(getApplicationContext(), "ID dan password anda tidak cocok.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(getApplicationContext(), "Selamat datang, " + _id, Toast.LENGTH_SHORT).show();

                            _menuIntent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(_menuIntent);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
