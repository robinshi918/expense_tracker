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
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.robin.app.expensetracker.data.ExchangeRate
import org.robin.app.expensetracker.data.Transaction
import org.robin.app.expensetracker.databinding.FragmentTransactionDetailBinding
import org.robin.app.expensetracker.ui.MyDatePickerDialog
import org.robin.app.expensetracker.util.Util
import org.robin.app.expensetracker.viewmodel.TransactionDetailViewModel
import java.util.*


/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

    companion object {
        // fragment argument 'transactionId' is -1 if user wants to create a new transaction
        const val INVALID_TRANSACTION_ID = -1
    }

    private val viewModel: TransactionDetailViewModel by viewModels()
    private val safeArgs: TransactionDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentTransactionDetailBinding

    private lateinit var transaction: Transaction
    private var transactionId: Int = INVALID_TRANSACTION_ID

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)

        setupClickListeners()

        transactionId = safeArgs.transactionId

        if (transactionId != INVALID_TRANSACTION_ID) {
            // init widget values with data from database
            viewModel.transaction.observe(viewLifecycleOwner) { t: Transaction ->
                initUI(t)
                transaction = t

                viewModel.refreshExchangeRate(transaction.date).asLiveData()
                    .observe(viewLifecycleOwner, currencyRateObserver)
            }
        } else {
            // init widget with empty values
            transaction = Transaction().also { transaction ->
                initUI(transaction)
            }
            viewModel.refreshExchangeRate(transaction.date).asLiveData()
                .observe(viewLifecycleOwner, currencyRateObserver)
        }

        // enable back button
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        return binding.root
    }

    private val currencyRateObserver: Observer<ExchangeRate> =
        Observer<ExchangeRate> { rate ->
            rate?.let {
                Log.e("Robin", "rate returned from livedata: ${rate.rate}")
                binding.tvCurrencyRate.text = "1 USD = ${rate.rate} NZD"
            }
        }

    private fun initUI(t: Transaction) {
        with(binding) {
            edittextAmount.setText("%.02f".format((t.amount.toFloat() / 100)))
            tvCategory.text = t.categoryName

            switchExpenseType.isChecked = true
            switchExpenseType.isChecked =
                t.expenseType == Transaction.EXPENSE_TYPE_EXPENSE

            switchCurrency.isChecked = true
            switchCurrency.isChecked =
                t.currency == Transaction.CURRENCY_TYPE_NZD
            tvDate.text = Util.calendar2String(t.date)
            transaction = t

            deleteBtn.visibility = if (transactionId == INVALID_TRANSACTION_ID)
                View.GONE else View.VISIBLE
        }
    }

    private fun setupClickListeners() {
        with(binding) {

            categoryContainer.setOnClickListener {
                findNavController().navigate(R.id.action_select_category)
            }

            // TODO replace this widget from Switch to DropDownList to support more currencies
            switchCurrency.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    switchCurrency.text = Transaction.CURRENCY_TYPE_NZD
                    tvCurrencyRate.visibility = View.GONE
                } else {
                    switchCurrency.text = Transaction.CURRENCY_TYPE_USD
                    tvCurrencyRate.visibility = View.VISIBLE
                }
            }

            saveBtn.setOnClickListener {
                with(transaction) {
                    amount = (edittextAmount.text.toString().toFloat() * 100).toInt()
                    currency =
                        if (switchCurrency.isChecked) Transaction.CURRENCY_TYPE_NZD else Transaction.CURRENCY_TYPE_USD
                    expenseType = if (switchExpenseType.isChecked)
                        Transaction.EXPENSE_TYPE_EXPENSE else Transaction.EXPENSE_TYPE_INCOME
                    categoryId = 0            //TODO get real category ID
                    categoryName = "Unknown"   //TODO get real category name
                }
                try {
                    viewModel.save(transaction)
                    findNavController().navigateUp()
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }

            deleteBtn.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.delete_transaction_dialog_title))
                    .setMessage(getString(R.string.delete_transaction_dialog_body))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.ok) { _, _ ->

                        viewModel.delete()

                        findNavController().navigateUp()
                    }
                    .setNegativeButton(R.string.cancel, null).show()
            }

            switchExpenseType.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    switchExpenseType.text = getString(R.string.expense_type_expense)
                } else {
                    switchExpenseType.text = getString(R.string.expense_type_income)
                }
            }

            dateContainer.setOnClickListener {

                val datePickerDialog: MyDatePickerDialog =
                    if (transactionId == INVALID_TRANSACTION_ID) {
                        MyDatePickerDialog()
                    } else {
                        val year = transaction.date[Calendar.YEAR]
                        val month = transaction.date[Calendar.MONTH]
                        val day = transaction.date[Calendar.DAY_OF_MONTH]
                        MyDatePickerDialog(true, year, month, day)
                    }
                datePickerDialog.setListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    with(transaction) {
                        date[Calendar.YEAR] = calendar[Calendar.YEAR]
                        date[Calendar.MONTH] = calendar[Calendar.MONTH]
                        date[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH]
                        tvDate.text = Util.calendar2String(date)

                        viewModel.refreshExchangeRate(date).asLiveData()
                            .observe(viewLifecycleOwner, currencyRateObserver)

                    }
                }
                datePickerDialog.show(requireFragmentManager(), "MonthYearPickerDialog")
            }
        }
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