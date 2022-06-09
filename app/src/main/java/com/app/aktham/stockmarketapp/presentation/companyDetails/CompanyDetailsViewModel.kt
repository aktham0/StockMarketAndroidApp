package com.app.aktham.stockmarketapp.presentation.companyDetails

import androidx.lifecycle.*
import com.app.aktham.stockmarketapp.domain.repository.StockRepository
import com.app.aktham.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailsViewModel @Inject constructor(
    private val saveStateHandle: SavedStateHandle,
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _state :MutableLiveData<CompanyDetailsState> =
        MutableLiveData(CompanyDetailsState())
    val state :LiveData<CompanyDetailsState> get() = _state

    init {
        viewModelScope.launch {
            val symbol = saveStateHandle.get<String>("symbol") ?: "TESLA"
            _state.value = _state.value?.copy(isLoading = true)
            val companyDetailsResult = async {stockRepository.getCompanyDetails(symbol)}
            val intradayDetailsResult = async {stockRepository.getIntradayDetails(symbol)}
            when(val result = companyDetailsResult.await()) {
                is Resource.Success -> {
                    _state.value = _state.value?.copy(
                        isLoading = false,
                        companyDetails = result.data,
                        errorMessage = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value?.copy(
                        isLoading = false,
                        companyDetails = null,
                        errorMessage = result.errorMessage
                    )
                }
                else -> Unit
            }

            when(val result = intradayDetailsResult.await()) {
                is Resource.Success -> {
                    _state.value = _state.value?.copy(
                        isLoading = false,
                        stockInfo = result.data ?: emptyList(),
                        errorMessage = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value?.copy(
                        isLoading = false,
                        companyDetails = null,
                        stockInfo = emptyList(),
                        errorMessage = result.errorMessage
                    )
                }
                else -> Unit
            }
        }
    }
}