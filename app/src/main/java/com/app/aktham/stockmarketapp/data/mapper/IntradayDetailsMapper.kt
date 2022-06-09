package com.app.aktham.stockmarketapp.data.mapper

import com.app.aktham.stockmarketapp.data.reomte.dto.IntradayDetailsDto
import com.app.aktham.stockmarketapp.domain.model.IntradayDetails
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayDetailsDto.toIntradayDetails(): IntradayDetails {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val dateFormat = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, dateFormat)
    return IntradayDetails(
        timestamp = localDateTime,
        close = close
    )
}
