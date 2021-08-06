package org.robin.app.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
@Entity(tableName = "transaction")
data class Transaction(
    @ColumnInfo(name = "categoryId") val categoryId: Int,
    @ColumnInfo(name = "categoryName") val categoryName: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "expenseType") val expenseType: Int,
    @ColumnInfo(name = "currency") val currency: String = "NZD",
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "transactionId") var transactionId: Int = 0,
    //val date: Date,
) {

    override fun toString(): String {
        return "Transaction(categoryId=$categoryId, categoryName='$categoryName', amount=$amount, expenseType=$expenseType, currency='$currency', transactionId=$transactionId)"
    }

    companion object {
        const val EXPENSE_TYPE_INCOME = 1
        const val EXPENSE_TYPE_EXPENSE = 2
    }
}