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
import kotlinx.coroutines.GlobalScope
import org.robin.app.expensetracker.adapter.TransactionAdapter
import org.robin.app.expensetracker.api.ExchangeRateService
import org.robin.app.expensetracker.data.AppDatabase
import org.robin.app.expensetracker.data.Transaction
import org.robin.app.expensetracker.databinding.FragmentTransactionListBinding
import org.robin.app.expensetracker.ui.MonthYearPickerDialog
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
            showDatePicker()
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
        // TODO show month picker dialog
        val pd = MonthYearPickerDialog().apply {
            setListener {view, year, month, dayOfMonth ->
                Log.e("Robin", "year=$year, month=$month, day=$dayOfMonth")
            }

        }.show(requireFragmentManager(), "MonthYearPickerDialog")
    }

    private fun navigateToNewTransaction() {
        //TODO jump to new transaction edit page
        /*val transactionId = 1
        val action = TransactionListFragmentDirections.showTransactionDetailAction(transactionId)
        findNavController().navigate(action)*/

        findNavController().navigate(R.id.action_show_transaction_detail)
    }
}