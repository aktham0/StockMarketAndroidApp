package com.app.aktham.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {

    @Insert
    suspend fun insertCompanyListing(
        companyListingEntity: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListing()

    @Query("""
        SELECT * 
        FROM COMPANYLISTINGENTITY
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
    """
    )
    suspend fun searchCompanyListing(query: String) :List<CompanyListingEntity>


}