package com.app.aktham.stockmarketapp.domain.model

import java.time.LocalDateTime

data class IntradayDetails(
    val timestamp: LocalDateTime,
    val close: Double
)
