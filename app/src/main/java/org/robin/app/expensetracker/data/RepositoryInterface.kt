package org.robin.app.expensetracker.data

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
interface RepositoryInterface {

    /**
     * get category list
     */
    fun getCategoryList(): List<Category>

    /**
     * get transaction list
     */
    fun getTransactionList(month: String): List<Transaction>

    /**
     * add or update a transaction
     */
    fun setTransaction(t: Transaction)

    /**
     * get budget list for a certain month
     */
    fun getBudgetList(month: String): List<Budget>

    /**
     * get latest exchange rate from NZD to USD
     */
    fun getCurrencyRate(): Float
}