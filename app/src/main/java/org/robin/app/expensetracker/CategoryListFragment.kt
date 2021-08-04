package org.robin.app.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.robin.app.expensetracker.databinding.FragmentCategoryListBinding
import org.robin.app.expensetracker.viewmodel.CategoryListViewModel

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
class CategoryListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryListBinding
    private val viewModel: CategoryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryListBinding.inflate(inflater, container, false)

        return binding.root
    }
}