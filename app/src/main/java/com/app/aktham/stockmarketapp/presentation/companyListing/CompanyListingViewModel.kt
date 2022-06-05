package com.app.aktham.stockmarketapp.presentation.companyListing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.aktham.stockmarketapp.domain.repository.StockRepository
import com.app.aktham.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.function.LongFunction
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val companyListingRepository: StockRepository
) : ViewModel() {

    val state = MutableStateFlow(CompanyListingState())

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingEvent) {
        when(event) {
            is CompanyListingEvent.Refresh -> {
                getCompanyListing(getFromRemote = true)
            }

            is CompanyListingEvent.OnSearchQueryChange -> {
                val searchQuery = event.query
                state.value = state.value.copy(
                    searchQuery = searchQuery
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getCompanyListing(searchQuery, false)
                }
            }
        }
    }

    private fun getCompanyListing(
        searchQuery: String = "",
        getFromRemote: Boolean
    ) {
        viewModelScope.launch {
            companyListingRepository.getCompanyListing(getFromRemote, searchQuery)
                .collect { result ->
                    when(result) {
                        is Resource.Loading -> {
                            state.value = state.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Error -> {
                            Log.e("CompanyListingViewModel", result.errorMessage.toString())
                            Unit
                        }
                        is Resource.Success -> {
                            result.data?.let { listData ->
                                state.value = state.value.copy(
                                    companies = listData
                                )
                            }
                        }
                    }
                }

        }
    }
}