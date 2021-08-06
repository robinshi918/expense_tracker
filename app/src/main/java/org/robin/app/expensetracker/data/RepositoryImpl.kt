package org.robin.app.expensetracker.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : Repository {

    override fun getCategoryList(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionList(month: String): Flow<List<Transaction>> =
        appDatabase.transactionDao().getAll()

    override fun getTransactionById(id: Int): Flow<Transaction> =
        appDatabase.transactionDao().findById(id)

    override fun setTransaction(transaction: Transaction) =
        appDatabase.transactionDao().insert(transaction)

    override fun deleteTransactionById(transactionId: Int) =
        appDatabase.transactionDao().deleteById(transactionId)

    override fun getBudgetList(month: String): Flow<List<Budget>> {
        TODO("Not yet implemented")
    }
}