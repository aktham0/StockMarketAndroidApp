package com.app.aktham.stockmarketapp.data.reomte

import com.app.aktham.stockmarketapp.util.Resource
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    fun getListingMarket(
        @Query("apiKey") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val BASE_URL = "https://www.alphavantage.co"
        const val API_KEY = "EFX9M5K1912ZQKVA"
    }
}