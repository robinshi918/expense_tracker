package org.robin.app.expensetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.robin.app.expensetracker.data.Transaction
import org.robin.app.expensetracker.data.TransactionDao
import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
@HiltViewModel
class TransactionListViewModel @Inject internal constructor(
    dao: TransactionDao
) : ViewModel() {

    var transactionList = MutableLiveData<List<Transaction>>()

    init {
        // TODO below are test data, remove them at last.
        val t1 = Transaction(1, "transport", 100, 1, "NZD"/*, Date()*/)
        val t2 = Transaction(2, "entertain", 200, 1, "NZD"/*, Date()*/)
        val t3 = Transaction(3, "food", 300, 1, "NZD"/*, Date()*/)
        val t4 = Transaction(3, "coke", 300, 1, "NZD"/*, Date()*/)

        val list = listOf(t1, t2, t3, t4)
        transactionList.value = list


//        Log.e("Robin", "number of transactions = ${transactionDao.getAll().size}")
    }
}