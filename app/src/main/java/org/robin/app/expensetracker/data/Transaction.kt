package org.robin.app.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
@Entity(tableName = "transaction")
data class Transaction(
    @ColumnInfo(name = "categoryId") var categoryId: Int = Category.INVALID_CATEGORY_ID,
    @ColumnInfo(name = "categoryName") var categoryName: String = "",
    @ColumnInfo(name = "amount") var amount: Int = 0,
    @ColumnInfo(name = "expenseType") var expenseType: Int = EXPENSE_TYPE_EXPENSE,
    @ColumnInfo(name = "currency") var currency: String = CURRENCY_TYPE_NZD,
    @ColumnInfo(name = "date") val date: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "exchangeRate") var exchangeRate: Float = 1.0f,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "transactionId") var transactionId: Int = DEFAULT_TRANSACTION_ID,
) {

    override fun toString(): String {
        return "Transaction(categoryId=$categoryId, categoryName='$categoryName', amount=$amount, expenseType=$expenseType, currency='$currency', transactionId=$transactionId)"
    }

    companion object {
        const val DEFAULT_TRANSACTION_ID = 0

        const val EXPENSE_TYPE_INCOME = 1
        const val EXPENSE_TYPE_EXPENSE = 2

        const val CURRENCY_TYPE_NZD = "NZD"
        const val CURRENCY_TYPE_USD = "USD"
    }
}