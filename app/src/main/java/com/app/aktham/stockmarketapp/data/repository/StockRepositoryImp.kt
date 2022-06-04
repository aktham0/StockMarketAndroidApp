package com.app.aktham.stockmarketapp.data.repository

import com.app.aktham.stockmarketapp.data.local.StockDao
import com.app.aktham.stockmarketapp.data.local.StockDataBase
import com.app.aktham.stockmarketapp.data.mapper.toCompanyListing
import com.app.aktham.stockmarketapp.data.reomte.StockApi
import com.app.aktham.stockmarketapp.domain.model.CompanyListing
import com.app.aktham.stockmarketapp.domain.repository.StockRepository
import com.app.aktham.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    private val stockApi: StockApi,
    private val stockDataBase: StockDataBase,
) : StockRepository {

    private val stockDao = stockDataBase.getStickDao()

    override suspend fun getCompanyListing(
        fetchRemoteData: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localCompanyListing =
                stockDao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localCompanyListing.map { it.toCompanyListing() }
            ))

            val isDbDataEmpty = localCompanyListing.isEmpty() && query.isBlank()
            val shouldGetDataFromCache = !isDbDataEmpty && !fetchRemoteData
            if (shouldGetDataFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }
            // get data from remote
            val remoteData = try {
                // todo get and pares csv file

            } catch (ex: IOException) {
                emit(Resource.Error(errorMessage = "Couldn't load data"))
                ex.printStackTrace()
            } catch (ex :HttpException) {
                emit(Resource.Error(errorMessage = "Couldn't load data"))
                ex.printStackTrace()
            }

        }
    }

}