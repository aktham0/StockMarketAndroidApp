package com.app.aktham.stockmarketapp.data.mapper

import com.app.aktham.stockmarketapp.data.local.CompanyListingEntity
import com.app.aktham.stockmarketapp.data.reomte.dto.CompanyDetailsDto
import com.app.aktham.stockmarketapp.domain.model.CompanyDetails
import com.app.aktham.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() :CompanyListing {
    return CompanyListing(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity() :CompanyListingEntity {
    return CompanyListingEntity(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun CompanyDetailsDto.toCompanyDetails() :CompanyDetails {
    return CompanyDetails(
        name = name ?: "",
        symbol = symbol ?: "",
        country = country ?: "",
        description = description ?: "",
        industry = industry ?: ""
    )
}