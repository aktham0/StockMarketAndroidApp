package com.app.aktham.stockmarketapp.data.csv

import com.app.aktham.stockmarketapp.data.mapper.toIntradayDetails
import com.app.aktham.stockmarketapp.data.reomte.dto.IntradayDetailsDto
import com.app.aktham.stockmarketapp.domain.model.IntradayDetails
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayDetailsParser @Inject constructor() : CSVParser<IntradayDetails> {
    override suspend fun parse(stream: InputStream): List<IntradayDetails> {
        val reader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            reader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    IntradayDetailsDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    ).toIntradayDetails()
                }
                .filter {
                    it.timestamp.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.timestamp.hour
                }
        }.also {
            reader.close()
        }
    }
}