package org.robin.app.expensetracker.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange
import org.robin.app.expensetracker.api.ExchangeRateService
import org.robin.app.expensetracker.data.Repository
import org.robin.app.expensetracker.data.Transaction
import javax.inject.Inject
import kotlin.concurrent.thread

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
): ViewModel() {

    private val transactionId: Int = savedStateHandle.get<Int>(TRANSACTION_ID_SAVED_STATE_KEY)!!
    val transaction = repo.getTransactionById(transactionId).asLiveData()

    fun save() {

        val t = Transaction(0, "sport", 100, 1, "NZD")
        viewModelScope.launch(Dispatchers.IO) {
            repo.setTransaction(t)
        }

        // TODO
//        thread {
//            Log.e("Robin", "save transaction changes. transactionID = $transactionId")
//            val item = repo.getTransactionById(1000)
//            Log.e("Robin", "transactionDetailViewModel No. of Ts = $item")
//        }

    }

    fun delete() {
        TODO("implement delete")
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