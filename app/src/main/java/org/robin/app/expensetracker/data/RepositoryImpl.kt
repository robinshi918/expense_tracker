package org.robin.app.expensetracker.data

import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : RepositoryInterface {

    override fun getCategoryList(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getTransactionList(month: String): List<Transaction> =
        appDatabase.transactionDao().getAll()


    override fun setTransaction(t: Transaction) {
        TODO("Not yet implemented")
    }

    override fun getBudgetList(month: String): List<Budget> {
        TODO("Not yet implemented")
    }
}