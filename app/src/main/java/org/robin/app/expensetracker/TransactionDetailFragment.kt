package org.robin.app.expensetracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.robin.app.expensetracker.data.Transaction
import org.robin.app.expensetracker.databinding.FragmentTransactionDetailBinding
import org.robin.app.expensetracker.ui.MonthYearPickerDialog
import org.robin.app.expensetracker.viewmodel.TransactionDetailViewModel
import java.util.*


/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

    private val detailViewModel: TransactionDetailViewModel by viewModels()
    private val safeArgs: TransactionDetailFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
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

            viewModel?.transaction?.observe(lifecycleOwner!!){
                Log.e("Robin", "transaction arrived!!!!1 \n $it")
            }

            categoryContainer.setOnClickListener {
                findNavController().navigate(R.id.action_select_category)
            }

            saveBtn.setOnClickListener {
                detailViewModel.save()
                findNavController().navigateUp()
            }

            deleteBtn.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.delete_transaction_dialog_title))
                    .setMessage(getString(R.string.delete_transaction_dialog_body))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        detailViewModel.delete()
                        findNavController().navigateUp()
                        Toast.makeText(requireContext(), getString(R.string.transaction_deleted), Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton(R.string.cancel, null).show()
            }

            currencySwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    currencySwitch.text = Transaction.CURRENCY_TYPE_NZD
                } else {
                    currencySwitch.text = Transaction.CURRENCY_TYPE_USD
                }
            }

            expenseTypeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    expenseTypeSwitch.text = getString(R.string.expense_type_expense)
                } else {
                    expenseTypeSwitch.text = getString(R.string.expense_type_income)
                }
            }

            dateContainer.setOnClickListener {
                MonthYearPickerDialog().apply {
                    setListener { _, year, month, dayOfMonth ->
                        Log.e("Robin", "year=$year, month=$month, day=$dayOfMonth")
                        val calendar = Calendar.getInstance()
                        calendar.set(year, month, 1)
                        date.text = String.format("%02d/%d", month, year)
                    }
                }.show(requireFragmentManager(), "MonthYearPickerDialog")
            }
        }

        // enable back button
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