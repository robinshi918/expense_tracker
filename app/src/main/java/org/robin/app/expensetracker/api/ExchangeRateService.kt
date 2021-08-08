package org.robin.app.expensetracker.api

import org.robin.app.expensetracker.data.ExchangeRateResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Web API to get currency exchange rate between NZD and USD
 * API 1: get full list of live exchange rates
 *  - http://api.currencylayer.com/live?access_key=08ae8c2209048c3e15396204ae30e2cc&format=1
 * API 2: get historical exchange rate for some currencies
 *  - http://api.currencylayer.com/historical?date=2021-09-01&currencies=NZD&access_key=08ae8c2209048c3e15396204ae30e2cc&format=1
 *
 * @author Robin Shi
 * @since 6/08/21
 */
interface ExchangeRateService {

    /**
     * get a historical exchange rate between USD and NZD
     * @param date date of the requested rate. Use default parameter if you want to get the
     * exchange rate for today. Parameter should be in the format of "yyyy-MM-dd".
     *
     * @param access_key developer access key. Please use default parameter unless you have another
     * developer key to use.
     *
     * @param format format of the returned JSON data. Please use default parameter.
     *
     * @param currencies request only some of the currencies. Parameter should be in the format like
     * "USD,NZD". Default parameter only show exchange rate for NZD based on USD as source.
     */
    @GET("historical")
    suspend fun getRate(
        @Query("date") date: String = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time),
        @Query("access_key") access_key: String = "08ae8c2209048c3e15396204ae30e2cc",
        @Query("format") format: Int = 1,
        @Query("currencies") currencies: String = "NZD"
    ): ExchangeRateResponse

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