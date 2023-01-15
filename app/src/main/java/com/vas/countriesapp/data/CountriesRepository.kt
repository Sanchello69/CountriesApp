package com.vas.countriesapp.data

import com.vas.countriesapp.data.model.CountryModelData
import com.vas.countriesapp.data.network.CountriesApi
import javax.inject.Inject

class CountriesRepository @Inject constructor(private val api: CountriesApi) {

    suspend fun getAllCountries(): List<CountryModelData> = api.getCountriesInfo()
    suspend fun getCountry(name: String): CountryModelData = api.getCountryInfo(name).first()

}