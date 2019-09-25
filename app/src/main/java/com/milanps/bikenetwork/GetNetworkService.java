package com.milanps.bikenetwork;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Milan Pop Stefanija
 */
public interface GetNetworkService {


    @GET("networks")
    Call<CompanyList> getAllCompanies();
}
