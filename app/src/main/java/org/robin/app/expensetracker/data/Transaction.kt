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
    /**
     * not used for now.
     * TODO move categoryName to category table, use categoryId as foreign key to refer to category table.
     */
    @ColumnInfo(name = "categoryId") var categoryId: Int = Category.INVALID_CATEGORY_ID,

    /**
     * name of category.
     */
    @ColumnInfo(name = "categoryName") var categoryName: String = "",

    /**
     * amount of the transaction. unit is cent. So conversion to dollar is needed for display
     */
    @ColumnInfo(name = "amount") var amount: Int = 0,

    /**
     * type of the transaction: income or expense
     */
    @ColumnInfo(name = "expenseType") var expenseType: Int = EXPENSE_TYPE_EXPENSE,

    /**
     * currency used in this transaction. Use standard currency code. For example NZD for
     * New Zealand Dollar, and USD for US Dollar.
     */
    @ColumnInfo(name = "currency") var currency: String = CURRENCY_TYPE_NZD,

    /**
     * epoc timestamp of the transaction date
     */
    @ColumnInfo(name = "date") val date: Calendar = Calendar.getInstance(),

    /**
     * exchange rate used in this transaction. The rate is the historical rate on the transaction date.
     * For now, NZD is used as base. so for NZD transaction, exchangeRate is 1.0f.
     * If exchange rate is 1.456, it means 1 USD = 1.456 NZD
     * TODO use exchangeRate ID as foreign key to get actual rate, in order to remove duplicate
     * exchange rate values both in 'transaction' table and 'exchange_rate' table
     */
    @ColumnInfo(name = "exchangeRate") var exchangeRate: Float = 1.0f,

    /**
     * primary key. auto incremental. Set this parameter to 0 to let database automatically generate
     * value for this.
     */
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