package com.haggai.login;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ForexAdapter extends RecyclerView.Adapter<ForexViewHolder> {
    private JSONObject _rates;
    private JSONArray _names;

    public ForexAdapter(JSONObject rates) {
        this._rates = rates;
        this._names = rates.names();
    }

    @NonNull
    @Override
    public ForexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_forex, parent, false);
        return new ForexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForexViewHolder holder, int position) {
        try {
            String kode = _names.getString(position);
            holder.kodeTextView.setText(kode);

            double kurs = _rates.getDouble(kode);
            DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
            String kursFormatted = decimalFormat.format(kurs);
            holder.kursTextView.setText(kursFormatted);
        } catch (JSONException e) {
            Log.e("*hgh*", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return _names.length();
    }
}
