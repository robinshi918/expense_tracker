package org.robin.app.expensetracker.data

import java.sql.Date

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
data class Transaction(
    val categoryId: Int,
    val amount: Int,
    val type: Int,
    val currency: String,
    val date: Date,
    val note: String
)