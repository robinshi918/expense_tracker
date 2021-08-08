package org.robin.app.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.robin.app.expensetracker.R
import org.robin.app.expensetracker.TransactionListFragmentDirections
import org.robin.app.expensetracker.data.Transaction
import org.robin.app.expensetracker.databinding.ListItemTransactionBinding
import org.robin.app.expensetracker.util.Util

/**
 *
 * @author Robin Shi
 * @since 3/08/21
 */
class TransactionAdapter :
    ListAdapter<Transaction, TransactionAdapter.ViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_transaction,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.transaction?.transactionId?.let { id ->
                    navigateToTransactionDetail(id, view)
                }
            }
        }

        private fun navigateToTransactionDetail(transactionId: Int, view: View) {
            val action =
                TransactionListFragmentDirections.actionShowTransactionDetail(transactionId)
            view.findNavController().navigate(action)
        }

        fun bind(t: Transaction) {
            with(binding) {
                transaction = t

                if (t.expenseType == Transaction.EXPENSE_TYPE_EXPENSE) {
                    ivExpenseType.setImageResource(R.drawable.minus_icon)
                } else {
                    ivExpenseType.setImageResource(R.drawable.plus_icon)
                }

                var displayValue: Float = t.amount.toFloat() / 100
                if (t.currency == Transaction.CURRENCY_TYPE_USD) {
                    ivOtherCurrency.visibility = View.VISIBLE
                    displayValue *= t.exchangeRate
                } else {
                    ivOtherCurrency.visibility = View.GONE
                }

                tvAmount.text = "%.02f".format(displayValue)

                tvDate.text = Util.calendar2String(t.date)
                executePendingBindings()
            }
        }
    }
}

private class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {

    override fun areItemsTheSame(
        oldItem: Transaction,
        newItem: Transaction
    ): Boolean {
        return oldItem.transactionId == newItem.transactionId
    }

    override fun areContentsTheSame(
        oldItem: Transaction,
        newItem: Transaction
    ): Boolean {
        return oldItem == newItem
    }
}