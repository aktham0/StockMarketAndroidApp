package com.app.aktham.stockmarketapp.data.reomte

import com.app.aktham.stockmarketapp.data.reomte.dto.CompanyDetailsDto
import com.app.aktham.stockmarketapp.util.Resource
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListingMarket(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyDetails(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("symbol") symbol: String
    ) :CompanyDetailsDto

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayDetails(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "60min"
    ): ResponseBody

    companion object {
        const val BASE_URL = "https://www.alphavantage.co"
        const val API_KEY = "EFX9M5K1912ZQKVA"
    }
}