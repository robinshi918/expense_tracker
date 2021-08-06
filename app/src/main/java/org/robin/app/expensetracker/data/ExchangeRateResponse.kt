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
    @field:SerializedName("timestamp") val timestamp: Long,
    @field:SerializedName("source") val source: String,
    @field:SerializedName("quotes") val quotes: Map<String, Float>,
) {
    override fun toString(): String {
        return "Response(success=$success, terms='$terms', privacy='$privacy', timestamp=$timestamp, source='$source', quotes=$quotes)"
    }

    fun getRate(toCurrency: String): Float? {
        return quotes["$BASE_CURRENCY$toCurrency"]
    }

    companion object {
        const val BASE_CURRENCY = "USD"
    }
}