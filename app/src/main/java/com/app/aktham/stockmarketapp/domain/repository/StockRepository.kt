package com.app.aktham.stockmarketapp.domain.repository

import com.app.aktham.stockmarketapp.domain.model.CompanyDetails
import com.app.aktham.stockmarketapp.domain.model.CompanyListing
import com.app.aktham.stockmarketapp.domain.model.IntradayDetails
import com.app.aktham.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListing(
        fetchRemoteData: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getCompanyDetails(
        symbol: String
    ): Resource<CompanyDetails>

    suspend fun getIntradayDetails(
        symbol: String
    ): Resource<List<IntradayDetails>>
}