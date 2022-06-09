package com.app.aktham.stockmarketapp.presentation.companyListing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.aktham.stockmarketapp.domain.repository.StockRepository
import com.app.aktham.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val companyListingRepository: StockRepository
) : ViewModel() {

    private val _state = MutableLiveData<CompanyListingState>(CompanyListingState())
    val state :LiveData<CompanyListingState> get() = _state

    private var searchJob: Job? = null

    init {
        getCompanyListing()
    }

    fun onEvent(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.Refresh -> {
                getCompanyListing(getFromRemote = true)
            }

            is CompanyListingEvent.OnSearchQueryChange -> {
                val searchQuery = event.query.lowercase()
                _state.value = CompanyListingState(searchQuery = searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getCompanyListing()
                }
            }
        }
    }

    private fun getCompanyListing(
        searchQuery: String = _state.value?.searchQuery?.lowercase() ?: "",
        getFromRemote: Boolean = false
    ) {
        if (getFromRemote){
            _state.value = _state.value?.copy(
                isRefreshing = true
            )
        }
        viewModelScope.launch {
            companyListingRepository.getCompanyListing(getFromRemote, searchQuery)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value?.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> {
                            _state.value = _state.value?.copy(
                                errorMessage = result.errorMessage ?: "",
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                        is Resource.Success -> {
                            result.data?.let { companyList ->
                                _state.value = _state.value?.copy(
                                    companies = companyList,
                                    isRefreshing = false
                                )
                            }
                        }
                    }
                }

        }
    }
}