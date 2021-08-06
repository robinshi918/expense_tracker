package org.robin.app.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.robin.app.expensetracker.databinding.FragmentTransactionDetailBinding
import org.robin.app.expensetracker.viewmodel.TransactionDetailViewModel
import org.robin.app.expensetracker.ui.MonthYearPickerDialog




/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

//    private lateinit var binding: FragmentTransactionDetailBinding

    private val detailViewModel: TransactionDetailViewModel by viewModels()
    private val safeArgs: TransactionDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentTransactionDetailBinding>(
            inflater,
            R.layout.fragment_transaction_detail,
            container,
            false
        ).apply {
            viewModel = detailViewModel
            lifecycleOwner = viewLifecycleOwner

            categoryContainer.setOnClickListener {
                findNavController().navigate(R.id.action_select_category)
            }

            saveBtn.setOnClickListener {
                detailViewModel.save()
            }

            deleteBtn.setOnClickListener {
                detailViewModel.delete()
            }

            currencySwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    currencySwitch.text = "NZD"
                } else {
                    currencySwitch.text = "USD"
                }
            }

            expenseTypeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    expenseTypeSwitch.text = "Expense"
                } else {
                    expenseTypeSwitch.text = "Income"
                }
            }

            dateContainer.setOnClickListener {
                val pd = MonthYearPickerDialog().apply {
                    setListener {view, year, month, dayOfMonth ->
                        Log.e("Robin", "year=$year, month=$month, day=$dayOfMonth")
                         }

                }.show(requireFragmentManager(), "MonthYearPickerDialog")
            }
        }

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        val transactionId = safeArgs.transactionId

        return binding.root
    }

    /**
     * handle BACK button click
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}