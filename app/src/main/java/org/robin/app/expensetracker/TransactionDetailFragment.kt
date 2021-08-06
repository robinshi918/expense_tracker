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

    private val viewModel: TransactionDetailViewModel by viewModels()
    private val safeArgs: TransactionDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentTransactionDetailBinding

    private var transaction: Transaction? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)

        viewModel.transaction.observe(viewLifecycleOwner){ t: Transaction ->
            transaction = t
        }
        val transactionId = safeArgs.transactionId

        binding.categoryContainer.setOnClickListener {
            findNavController().navigate(R.id.action_select_category)
        }

        binding.apply {

            saveBtn.setOnClickListener {
                viewModel.save()
                findNavController().navigateUp()
            }

            deleteBtn.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.delete_transaction_dialog_title))
                    .setMessage(getString(R.string.delete_transaction_dialog_body))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        viewModel.delete()
                        findNavController().navigateUp()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.transaction_deleted),
                            Toast.LENGTH_LONG
                        ).show()
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

        return binding.root
    }

    /**
     * handle BACK button in Action Bar
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