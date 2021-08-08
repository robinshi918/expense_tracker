package org.robin.app.expensetracker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.robin.app.expensetracker.CategoryListFragment
import org.robin.app.expensetracker.CategoryListFragment.Companion.CATEGORY_ARGUMENT_KEY
import org.robin.app.expensetracker.CategoryListFragment.Companion.REQUEST_KEY
import org.robin.app.expensetracker.R
import org.robin.app.expensetracker.data.Category
import org.robin.app.expensetracker.databinding.ListItemCategoryBinding

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */
class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    companion object {
        private val TAG = CategoryAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_category,
                parent,
                false
            )
        )
    }

    class ViewHolder(private val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                val name = binding.category!!.name
                Log.d(TAG, "User selected category: $name")
                val fragment = view.findFragment<CategoryListFragment>()
                val bundle = bundleOf(CATEGORY_ARGUMENT_KEY to name)
                fragment.setFragmentResult(REQUEST_KEY, bundle)
                view.findNavController().navigateUp()
            }
        }

        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.name == oldItem.name
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem == newItem
    }
}