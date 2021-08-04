package org.robin.app.expensetracker.data

import java.util.*

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
data class Transaction(
    val transactionId: Int,
    val categoryId: Int,
    val categoryName: String,
    val amount: Int,
    val expenseType: Int,
    val currency: String = "NZD",
    val date: Date,
)