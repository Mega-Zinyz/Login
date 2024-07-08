package com.haggai.login;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CuacaAdapter extends RecyclerView.Adapter<CuacaViewHolder> {
    private Activity _activity;
    private List<CuacaListModel> listModelList;
    private CuacaRootModel _rootmodel;

    public CuacaAdapter(Activity activity, CuacaRootModel rm) {
        this._activity = activity;
        this._rootmodel = rm;

        listModelList = rm.getListModels();
    }

    @NonNull
    @Override
    public CuacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cuaca, parent,false);
        return new CuacaViewHolder(view);
    }

    private double toCelcius(double kelvin) { return kelvin - 272.15; }

    private String formatNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        return decimalFormat.format(number);
    }

    @Override
    public void onBindViewHolder(@NonNull CuacaViewHolder holder, int position) {
        CuacaListModel lm = listModelList.get(position);
        CuacaWeatherModel wm = lm.getWeatherModels().get(0);
        CuacaMainModel mm = lm.getMainModel();

        String suhu = formatNumber(toCelcius(mm.getTemp_min())) + "°C - " + formatNumber(toCelcius(mm.getTemp_max())) + "°C";

        String iconurl = "https://openweathermap.org/img/wn/" + wm.getIcon() + "@2x.png";
        Picasso.with(_activity)
                .load(iconurl)
                .error(R.drawable.baseline_error_24)
                .into(holder.cuacaImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("*hgh*", "Image loaded successfully: " + iconurl);
                    }

                    @Override
                    public void onError() {
                        Log.e("*hgh*", "Error loading image: " + iconurl);
                    }
                });


        String tanggalWaktuWib = formatWib(lm.getDt_txt());

        holder.namaTextView.setText(wm.getMain());
        holder.deskripsiTextView.setText(wm.getDescription());
        holder.tglWaktuTextView.setText(tanggalWaktuWib);
        holder.suhuTextView.setText(suhu);
    }

    private String formatWib(String tanggalWaktuGMT_string) {
        Log.d("*hgh*", "Waktu GMT : " + tanggalWaktuGMT_string);

        Date tanggalWaktuGmt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            tanggalWaktuGmt = sdf.parse(tanggalWaktuGMT_string);
        } catch (ParseException e) {
            Log.e("*hgh*", e.getMessage());
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(tanggalWaktuGmt);
        calendar.add(Calendar.HOUR_OF_DAY, 7);

        Date tanggalWaktuWib = calendar.getTime();

        String tanggalWaktuWib_String = sdf.format(tanggalWaktuWib);
        tanggalWaktuWib_String = tanggalWaktuWib_String.replace("00:00", "00 WIB");

        Log.d("*hgh*", "Tanggal Waktu Indonesia Barat : " + tanggalWaktuWib_String);

        return tanggalWaktuWib_String;
    }


    @Override
    public int getItemCount() {
        return (listModelList != null) ? listModelList.size() : 0;
    }
}
