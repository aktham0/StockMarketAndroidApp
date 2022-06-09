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

private const val TAG = "CompanyListingViewModel"

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val companyListingRepository: StockRepository
) : ViewModel() {

    private val _state = MutableLiveData<CompanyListingState>(CompanyListingState())
    val state :LiveData<CompanyListingState> get() = _state

    private var searchJob: Job? = null

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
        viewModelScope.launch {
            companyListingRepository.getCompanyListing(getFromRemote, searchQuery)
                .collect { result ->
                    Log.d(TAG, "Company Listing Result-> ${result.toString()}")
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value?.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> {
                            Log.e(TAG, result.errorMessage.toString())
                            _state.value = _state.value?.copy(
                                errorMessage = result.errorMessage ?: "",
                                isLoading = false
                            )
                        }
                        is Resource.Success -> {
                            result.data?.let { companyList ->
                                _state.value = _state.value?.copy(
                                    companies = companyList
                                )
                            }
                        }
                    }
                }

        }
    }
}