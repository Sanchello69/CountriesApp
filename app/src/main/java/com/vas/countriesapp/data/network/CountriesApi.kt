package com.vas.countriesapp.data.network

import com.vas.countriesapp.data.model.CountryModelData
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {

    @GET("all")
    suspend fun getCountriesInfo(): List<CountryModelData>

    @GET("name/{name}?fullText=true")
    suspend fun getCountryInfo(@Path("name") name: String): List<CountryModelData>

}