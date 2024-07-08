package com.haggai.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CuacaListModel {

    @SerializedName("main")
    private CuacaMainModel mainModel;
    @SerializedName("weather")
    private List<CuacaWeatherModel> weatherModels;
    private String dt_txt;

    public CuacaListModel() {}

    public CuacaMainModel getMainModel() {
        return mainModel;
    }

    public void setMainModel(CuacaMainModel mainModel) {
        this.mainModel = mainModel;
    }

    public List<CuacaWeatherModel> getWeatherModels() {
        return weatherModels;
    }

    public void setWeatherModels(List<CuacaWeatherModel> weatherModels) {
        this.weatherModels = weatherModels;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}