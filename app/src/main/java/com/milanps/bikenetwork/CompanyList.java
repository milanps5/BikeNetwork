package com.milanps.bikenetwork;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Milan Pop Stefanija
 */
public class CompanyList {

    @SerializedName("networks")
    private List <CompanyDTO> companies;

    public List<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDTO> companies) {
        this.companies = companies;
    }
}
