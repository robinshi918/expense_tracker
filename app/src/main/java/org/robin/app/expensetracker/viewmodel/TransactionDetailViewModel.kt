package org.robin.app.expensetracker.viewmodel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.robin.app.expensetracker.api.ExchangeRateService
import org.robin.app.expensetracker.data.ExchangeRate
import org.robin.app.expensetracker.data.Repository
import org.robin.app.expensetracker.data.Transaction
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@HiltViewModel
class TransactionDetailViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: Repository,
) : ViewModel() {

    private val transactionId: Int = savedStateHandle.get<Int>(TRANSACTION_ID_SAVED_STATE_KEY)!!
    val transaction = repo.getTransactionById(transactionId).asLiveData()

    val exchangeRate = MutableLiveData<ExchangeRate>()

    @Throws(IllegalArgumentException::class)
    fun save(t: Transaction) {
        checkUserInput(t)
        viewModelScope.launch(Dispatchers.IO) {
            repo.setTransaction(t)
        }
    }

    /**
     * checks user input and throws an IllegalArgumentException with error message
     * if user input is not valid.
     */
    @Throws(IllegalArgumentException::class)
    private fun checkUserInput(transaction: Transaction) {
        // TODO move hardcoded string to string resource
        if (transaction.amount <= 0) {
            throw IllegalArgumentException("Transaction value can not be zero.")
        } else if (transaction.categoryId == -1) {
            throw IllegalArgumentException("Please select a category.")
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTransactionById(transactionId)
        }
    }

    fun refreshExchangeRate(date: Calendar): Flow<ExchangeRate> {
        val param = SimpleDateFormat("yyyy-MM-dd").format(date.time)
        return repo.getExchangeRate(param, Transaction.CURRENCY_TYPE_USD, Transaction.CURRENCY_TYPE_NZD)
    }

    companion object {
        // transaction id parameter passed from TransactionListFragment
        private const val TRANSACTION_ID_SAVED_STATE_KEY = "transactionId"
    }
}