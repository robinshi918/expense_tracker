package org.robin.app.expensetracker.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.robin.app.expensetracker.api.ExchangeRateService
import org.robin.app.expensetracker.data.Repository
import org.robin.app.expensetracker.data.Transaction
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
    private val service: ExchangeRateService
) : ViewModel() {

    private val transactionId: Int = savedStateHandle.get<Int>(TRANSACTION_ID_SAVED_STATE_KEY)!!
    val transaction = repo.getTransactionById(transactionId).asLiveData()

    @Throws(IllegalArgumentException::class)
    fun save(t: Transaction) {
        // validate user input first. Data won't be saved if validation failed.
        checkUserInput(t)
        viewModelScope.launch(Dispatchers.IO) {
            repo.setTransaction(t)
        }
    }

    /**
     * checks user input and throws an instance of IllegalArgumentException with error message
     */
    @Throws(IllegalArgumentException::class)
    private fun checkUserInput(transaction: Transaction) {
        if (transaction.amount <= 0) {
            throw IllegalArgumentException("Transaction amount can not be zero.")
        } else if (transaction.categoryId == -1) {
            throw IllegalArgumentException("Please select a category.")
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTransactionById(transactionId)
        }
    }

    private fun testNetworkService() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getRate()
            Log.e("Robin", "current rate = ${response.getRate("NZD")}")
        }
    }

    companion object {
        // transaction id parameter passed from TransactionListFragment
        private const val TRANSACTION_ID_SAVED_STATE_KEY = "transactionId"
    }
}