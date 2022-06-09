package com.app.aktham.stockmarketapp.presentation.companyDetails

import com.app.aktham.stockmarketapp.domain.model.CompanyDetails
import com.app.aktham.stockmarketapp.domain.model.IntradayDetails

data class CompanyDetailsState(
    val stockInfo: List<IntradayDetails> = emptyList(),
    val companyDetails: CompanyDetails? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
