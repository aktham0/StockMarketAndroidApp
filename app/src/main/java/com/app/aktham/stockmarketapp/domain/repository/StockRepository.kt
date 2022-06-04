package com.app.aktham.stockmarketapp.domain.repository

import com.app.aktham.stockmarketapp.domain.model.CompanyListing
import com.app.aktham.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListing(
        fetchRemoteData: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}