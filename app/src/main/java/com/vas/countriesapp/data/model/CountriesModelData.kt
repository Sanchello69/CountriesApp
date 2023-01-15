package com.vas.countriesapp.data.model

import com.google.gson.annotations.SerializedName

data class CountryModelData(
    @SerializedName("name")
    val name: String,

    @SerializedName("capital")
    val capital: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("flag")
    val flagsUrl: String,

    @SerializedName("timezones")
    val timezones: List<String>,

    @SerializedName("currencies")
    val currencies: List<CurrencyModelData>,
)

data class CurrencyModelData(
    @SerializedName("name")
    val name: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("symbol")
    val symbol: String,
) {
    override fun toString(): String {
        return "$name $symbol ($code)"
    }
}