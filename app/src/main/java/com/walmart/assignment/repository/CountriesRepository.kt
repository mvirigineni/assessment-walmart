package com.walmart.assignment.repository

import com.walmart.assignment.data.Country
import com.walmart.assignment.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class CountriesRepository {
    private val api = RetrofitClient.countriesApi

    suspend fun getCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            val countries = api.getCountries()
            if (countries.isEmpty()) {
                Result.failure(Exception("Empty response"))
            } else {
                Result.success(countries)
            }
        } catch (e: HttpException) {
            when (e.code()) {
                in 500..599 -> Result.failure(Exception("Server error"))
                else -> Result.failure(Exception("HTTP error: ${e.code()}"))
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(Exception("Timeout"))
        } catch (e: IOException) {
            Result.failure(Exception("Network error"))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error"))
        }
    }
} 