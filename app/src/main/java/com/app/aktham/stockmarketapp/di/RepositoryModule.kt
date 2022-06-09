package com.app.aktham.stockmarketapp.di

import com.app.aktham.stockmarketapp.data.csv.CSVParser
import com.app.aktham.stockmarketapp.data.csv.CompanyListingParser
import com.app.aktham.stockmarketapp.data.csv.IntradayDetailsParser
import com.app.aktham.stockmarketapp.data.repository.StockRepositoryImp
import com.app.aktham.stockmarketapp.domain.model.CompanyListing
import com.app.aktham.stockmarketapp.domain.model.IntradayDetails
import com.app.aktham.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayParser(
        intradayParcer: IntradayDetailsParser
    ): CSVParser<IntradayDetails>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImp: StockRepositoryImp
    ): StockRepository
    
}