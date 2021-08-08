package org.robin.app.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.robin.app.expensetracker.adapter.CategoryAdapter
import org.robin.app.expensetracker.databinding.FragmentCategoryListBinding
import org.robin.app.expensetracker.viewmodel.CategoryListViewModel

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    companion object {
        const val CATEGORY_ARGUMENT_KEY = "category_name_key"
        const val REQUEST_KEY = "CategoryListFragment_REQUEST_KEY"
    }

    private lateinit var binding: FragmentCategoryListBinding
    private val viewModel: CategoryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryListBinding.inflate(inflater, container, false)

        val adapter = CategoryAdapter()
        binding.categoryListRecyclerview.adapter = adapter
        subscribeUi(adapter, binding)

        binding.addBtn.setOnClickListener {
            Toast.makeText(requireContext(), "TODO: add new category", Toast.LENGTH_LONG).show()
        }

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun subscribeUi(adapter: CategoryAdapter, binding: FragmentCategoryListBinding) {
        viewModel.categoryList.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}