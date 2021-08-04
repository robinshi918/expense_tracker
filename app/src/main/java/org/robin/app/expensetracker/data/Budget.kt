package org.robin.app.expensetracker.data

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
data class Budget(
    // category ID
    val categoryId: Int,
    // month of the budget, in format "202108"
    val date: String,
    // amount of the budget, currency type is NZD
    val amount: Int,
)
