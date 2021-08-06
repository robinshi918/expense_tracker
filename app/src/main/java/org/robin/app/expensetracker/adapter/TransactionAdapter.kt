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