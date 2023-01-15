package com.vas.countriesapp.presentation.listCountries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vas.countriesapp.data.CountriesRepository
import com.vas.countriesapp.data.model.CountryModelData
import com.vas.countriesapp.utils.doSingleRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.vas.countriesapp.utils.RemoteResult

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: CountriesRepository) : ViewModel(){

    private val _listCountries = MutableLiveData<RemoteResult<List<CountryModelData>>>()
    val listCountries: LiveData<RemoteResult<List<CountryModelData>>>
        get() = _listCountries

    init {
        getListCountries()
    }

    fun getListCountries(){
        doSingleRequest(
            query = {repository.getAllCountries()},
            doOnLoading = {
                _listCountries.value = RemoteResult.loading()
            },
            doOnSuccess = {
                _listCountries.value = RemoteResult.success(it)
            },
            doOnError = {
                _listCountries.value = RemoteResult.error(it.message)
            }
        )
    }

}