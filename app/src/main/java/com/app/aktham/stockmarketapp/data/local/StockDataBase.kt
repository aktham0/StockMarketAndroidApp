package com.app.aktham.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDataBase : RoomDatabase() {
    abstract fun getStickDao() :StockDao
}