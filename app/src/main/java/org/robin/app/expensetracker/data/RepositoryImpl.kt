package org.robin.app.expensetracker.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.robin.app.expensetracker.api.ExchangeRateService
import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val exchangeRateService: ExchangeRateService
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

    override fun getExchangeRate(date: String, source: String, target: String): Flow<ExchangeRate> {
        GlobalScope.launch(Dispatchers.IO) {
            refreshExchangeRate(date, source, target)
        }
        return appDatabase.exchangeRateDao().getRateByDate(date, source, target)
    }

    private suspend fun refreshExchangeRate(date: String, source: String, target: String) {
        // TODO implement an in-memory cache, so no need to check database each time
        val numOfRate = appDatabase.exchangeRateDao().hasRate(date, source, target)
        Log.e("Robin", "number of exchange rate for $date = $numOfRate")
        if (numOfRate == 0) {
            val response = exchangeRateService.getRate(date)
            Log.e("Robin", "network query result = $response")
            if (response.success) {
                val rate = ExchangeRate(date, response.getRate(target)!!, source, target)
                appDatabase.exchangeRateDao().insert(rate)
            }
        }
    }
}