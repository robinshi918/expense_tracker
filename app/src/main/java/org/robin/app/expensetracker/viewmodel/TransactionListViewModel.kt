package org.robin.app.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.robin.app.expensetracker.data.Repository
import org.robin.app.expensetracker.data.Transaction
import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
@HiltViewModel
class TransactionListViewModel @Inject internal constructor(
    repo: Repository
) : ViewModel() {
    // TODO only show the transactions by month
    val transactionList: LiveData<List<Transaction>> =
        repo.getTransactionList("").asLiveData()
}