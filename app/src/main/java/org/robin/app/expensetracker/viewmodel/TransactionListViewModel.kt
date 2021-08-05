package org.robin.app.expensetracker.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.robin.app.expensetracker.data.RepositoryInterface
import org.robin.app.expensetracker.data.Transaction
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
@HiltViewModel
class TransactionListViewModel @Inject internal constructor(
    repo: RepositoryInterface
) : ViewModel() {

    var transactionList = MutableLiveData<List<Transaction>>()

    init {
        // TODO remove this thread code.
        thread {
            val list = repo.getTransactionList("")
            Handler(Looper.getMainLooper()).post {
                transactionList.value = list
            }
        }
    }
}