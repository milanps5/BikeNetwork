package com.milanps.bikenetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Milan Pop Stefanija
 */
public class LocationDTO {
    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("country")
    @Expose
    private String country;


    @SerializedName("latitude")
    @Expose
    private Float latitude;


    @SerializedName("longitude")
    @Expose
    private Float longitude;


    public LocationDTO(){}

    public LocationDTO(String city, String country, Float latitude, Float longitude) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
