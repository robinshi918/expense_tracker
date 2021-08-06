package org.robin.app.expensetracker.api

import org.robin.app.expensetracker.data.exchange.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author Robin Shi
 * @since 6/08/21
 */
interface ExchangeRateService {
    /**
     * Web API to get currency exchange rate.
     * API: http://api.currencylayer.com/live?access_key=08ae8c2209048c3e15396204ae30e2cc&format=1
     */
    @GET("live")
    suspend fun getRate(
        @Query("access_key") query: String = "08ae8c2209048c3e15396204ae30e2cc",
        @Query("format") page: Int = 1
    ): Response

    companion object {
        private const val BASE_URL = "http://api.currencylayer.com/"

        fun create(): ExchangeRateService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExchangeRateService::class.java)
        }
    }
}