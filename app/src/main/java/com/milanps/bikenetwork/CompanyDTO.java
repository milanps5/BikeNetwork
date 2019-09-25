package com.milanps.bikenetwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Milan Pop Stefanija
 */
public class CompanyDTO {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("location")
    @Expose
    private LocationDTO location;

    @SerializedName("name")
    @Expose
    private String name;

    public CompanyDTO() {}

    public CompanyDTO(String id, LocationDTO locationDTO, String name) {
        this.id = id;
        this.location = locationDTO;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public LocationDTO getLocationDTO() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocationDTO(LocationDTO locationDTO) {
        this.location = locationDTO;
    }

    public void setName(String name) {
        this.name = name;
    }


}
