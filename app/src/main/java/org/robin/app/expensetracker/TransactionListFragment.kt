package org.robin.app.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.robin.app.expensetracker.adapter.TransactionAdapter
import org.robin.app.expensetracker.databinding.FragmentTransactionListBinding
import org.robin.app.expensetracker.ui.MyDatePickerDialog
import org.robin.app.expensetracker.util.Util
import org.robin.app.expensetracker.viewmodel.TransactionListViewModel
import java.util.*

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
        binding.rvTransactionList.adapter = adapter
        subscribeUi(adapter)

        binding.btnAdd.setOnClickListener {
            navigateToNewTransaction()
        }

        binding.tvDate.setOnClickListener {
            showDatePicker()
        }

        // TODO load transactions according to month and year, and show date info in title
        binding.tvDate.text = Util.calendar2StringWithoutDay(Calendar.getInstance())

        // hide BACK button in action bar
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    private fun subscribeUi(adapter: TransactionAdapter) {
        viewModel.transactionList.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    private fun showDatePicker() {
        MyDatePickerDialog(false).apply {
            setListener { _, year, month, dayOfMonth ->
                val cal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                binding.tvDate.text = Util.calendar2StringWithoutDay(cal)
            }
        }.show(requireFragmentManager(), "MonthYearPickerDialog")
    }

    private fun navigateToNewTransaction() {
        findNavController().navigate(R.id.action_show_transaction_detail)
    }
}