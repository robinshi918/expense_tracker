package org.robin.app.expensetracker.data

import kotlinx.coroutines.flow.Flow

/**
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
     * add or update a transaction
     */
    fun setTransaction(transaction: Transaction)

    /**
     * get budget list for a certain month
     */
    fun getBudgetList(month: String): Flow<List<Budget>>
}