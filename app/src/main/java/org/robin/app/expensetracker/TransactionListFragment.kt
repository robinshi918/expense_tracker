package org.robin.app.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.robin.app.expensetracker.adapter.TransactionAdapter
import org.robin.app.expensetracker.databinding.FragmentTransactionListBinding
import org.robin.app.expensetracker.viewmodel.TransactionListViewModel

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
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
        Toast.makeText(context, "show date picker", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToNewTransaction() {
        //TODO jump to new transaction edit page
        /*val transactionId = 1
        val action = TransactionListFragmentDirections.showTransactionDetailAction(transactionId)
        findNavController().navigate(action)*/

        findNavController().navigate(R.id.action_show_transaction_detail)
    }
}