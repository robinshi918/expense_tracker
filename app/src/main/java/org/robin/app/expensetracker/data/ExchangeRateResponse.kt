package org.robin.app.expensetracker.data

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Robin Shi
 * @since 6/08/21
 */
data class ExchangeRateResponse(
    @field:SerializedName("success") val success: Boolean,
    @field:SerializedName("terms") val terms: String,
    @field:SerializedName("privacy") val privacy: String,
    @field:SerializedName("historical") val historical: Boolean,
    @field:SerializedName("date") val date: String,
    @field:SerializedName("timestamp") val timestamp: Long,
    @field:SerializedName("source") val source: String,
    @field:SerializedName("quotes") val quotes: Map<String, Float>,
) {
    fun getRate(toCurrency: String): Float? {
        return if (success)
            quotes["$BASE_CURRENCY$toCurrency"]
        else
            null
    }

    override fun toString(): String {
        return "ExchangeRateResponse(success=$success, terms='$terms', " +
                "privacy='$privacy', historical=$historical, date='$date', " +
                "timestamp=$timestamp, source='$source', quotes=$quotes)"
    }

    companion object {
        const val BASE_CURRENCY = "USD"
    }
}