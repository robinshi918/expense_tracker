package org.robin.app.expensetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.robin.app.expensetracker.data.Transaction
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
class TransactionListViewModel : ViewModel() {

    var transactionList = MutableLiveData<List<Transaction>>()

    init {
        // TODO below are test data, remove them at last.
        val t1 = Transaction( 1, "transport", 100, 1, "NZD"/*, Date()*/)
        val t2 = Transaction( 2, "entertain", 200, 1, "NZD"/*, Date()*/)
        val t3 = Transaction( 3, "food", 300, 1, "NZD"/*, Date()*/)

        val list = listOf(t1, t2, t3)
        transactionList.value = list
    }
}