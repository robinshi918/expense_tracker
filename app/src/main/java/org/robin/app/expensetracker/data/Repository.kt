package org.robin.app.expensetracker.data

import kotlinx.coroutines.flow.Flow

/**
 *  Repository interface that UI layer uses.
 *
 * @author Robin Shi
 * @since 3/08/21
 */
interface Repository {

    /**
     * get category list
     */
    fun getCategoryList(): Flow<List<Category>>

    /**
     * get transaction list
     */
    fun getTransactionList(month: String): Flow<List<Transaction>>

    /**
     * get a single transaction
     */
    fun getTransactionById(id: Int): Flow<Transaction>

    /**
     * add or update a transaction
     */
    fun setTransaction(transaction: Transaction)

    /**
     * delete transaction by ID
     */
    fun deleteTransactionById(transactionId: Int)

    /**
     * get budget list for a certain month
     */
    fun getBudgetList(month: String): Flow<List<Budget>>

    /**
     * get exchange rate in a certain date for given 2 currencies
     */
    fun getExchangeRate(date: String, source: String, target: String): Flow<ExchangeRate>
}