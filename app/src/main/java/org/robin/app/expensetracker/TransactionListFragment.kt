package org.robin.app.expensetracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.robin.app.expensetracker.adapter.TransactionAdapter
import org.robin.app.expensetracker.data.AppDatabase
import org.robin.app.expensetracker.data.Transaction
import org.robin.app.expensetracker.databinding.FragmentTransactionListBinding
import org.robin.app.expensetracker.viewmodel.TransactionListViewModel
import kotlin.concurrent.thread

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
@AndroidEntryPoint
class TransactionListFragment : Fragment() {

    private lateinit var binding: FragmentTransactionListBinding

    private val viewModel: TransactionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransactionListBinding.inflate(inflater, container, false)

        val adapter = TransactionAdapter()
        binding.transactionList.adapter = adapter

        binding.addBtn.setOnClickListener {
            navigateToNewTransaction()
        }

        binding.date.setOnClickListener {
//            showDatePicker()
            testDatabase()
        }

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: TransactionAdapter, binding: FragmentTransactionListBinding) {
        viewModel.transactionList.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    private fun showDatePicker() {
        Toast.makeText(context, "show date picker", Toast.LENGTH_SHORT).show()
    }

    private fun testDatabase() {
        thread {
            val dao = AppDatabase.getInstance(requireActivity().applicationContext).transactionDao()
            Log.e("Robin", "size of transactions = ${dao.getAll().size}" )
            val t1 = Transaction( 1, "transport", 100, 1, "NZD"/*, Date()*/)
            dao.insert(t1)
            Log.e("Robin", "size of transactions = ${dao.getAll().size}" )
            val t2 = dao.findById(4)
            Log.e("Robin", "size of transactions for id(4) = ${t2.size}" )
            if (t2.isEmpty()) {
                Log.e("Robin", "id 4 not found")
            } else {
                Log.e("Robin", "id 4 found. deleting it")
                dao.delete(t2[0])
                Log.e("Robin", "size of transactions for id(4) = ${dao.findById(4).size}" )
            }
        }

    }

    private fun navigateToNewTransaction() {
        //TODO jump to new transaction edit page
        /*val transactionId = 1
        val action = TransactionListFragmentDirections.showTransactionDetailAction(transactionId)
        findNavController().navigate(action)*/

        findNavController().navigate(R.id.action_show_transaction_detail)
    }
}