package com.vas.countriesapp.presentation.detailsCountry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vas.countriesapp.data.CountriesRepository
import com.vas.countriesapp.data.model.CountryModelData
import com.vas.countriesapp.utils.RemoteResult
import com.vas.countriesapp.utils.doSingleRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: CountriesRepository,
                                           private val state: SavedStateHandle)
    :ViewModel() {

    private val _detailsCountry = MutableLiveData<RemoteResult<CountryModelData>>()
    val detailsCountry: LiveData<RemoteResult<CountryModelData>>
        get() = _detailsCountry

    init {
        state.get<String>("name")?.let { getDetailsCountry(it) }
    }

    private fun getDetailsCountry(name: String){
        doSingleRequest(
            query = {repository.getCountry(name)},
            doOnLoading = {
                _detailsCountry.value = RemoteResult.loading()
            },
            doOnSuccess = {
                _detailsCountry.value = RemoteResult.success(it)
            },
            doOnError = {
                _detailsCountry.value = RemoteResult.error(it.message)
            }
        )
    }

}