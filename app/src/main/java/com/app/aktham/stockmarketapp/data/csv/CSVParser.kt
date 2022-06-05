package com.app.aktham.stockmarketapp.data.csv

import com.app.aktham.stockmarketapp.domain.model.CompanyListing
import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(stream: InputStream): List<CompanyListing>
}