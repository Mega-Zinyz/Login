package com.haggai.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CuacaRootModel {
    @SerializedName("list")
    private List<CuacaListModel> listModels;
    @SerializedName("city")
    private CityModel cityModel;
    public CuacaRootModel() { }

    public List<CuacaListModel> getListModels() { return listModels; }

    public void setListModels(List<CuacaListModel> listModels) {
        this.listModels = listModels;
    }

    public CityModel getCityModel() { return cityModel; }

    public void setCityModel(CityModel cityModel) { this.cityModel = cityModel; }
}