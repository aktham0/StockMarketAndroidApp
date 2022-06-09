package com.app.aktham.stockmarketapp.data.reomte.dto

import com.squareup.moshi.Json

data class CompanyDetailsDto(
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Industry") val industry: String?
)
