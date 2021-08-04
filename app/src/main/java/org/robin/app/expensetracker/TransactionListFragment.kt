package org.robin.app.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.robin.app.expensetracker.databinding.FragmentTransactionDetailBinding
import org.robin.app.expensetracker.databinding.FragmentTransactionListBinding

/**
 *
 * @author Robin Shi
 * @since 4/08/21
 */
class TransactionListFragment : Fragment(){

    private lateinit var binding: FragmentTransactionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransactionListBinding.inflate(inflater, container, false)

        // TODO 1: set adapter
        // TODO 2: set button click listener
        // TODO 3: subscribeUi

        return binding.root

    }

    private fun subscribeUi() {
        TODO("to implement to subscribe to viewmodel data change")
    }


}