package org.robin.app.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.robin.app.expensetracker.databinding.FragmentTransactionDetailBinding
import org.robin.app.expensetracker.viewmodel.TransactionDetailViewModel

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
class TransactionDetailFragment : Fragment() {

    private lateinit var binding: FragmentTransactionDetailBinding

    private val viewModel: TransactionDetailViewModel by viewModels()
    private val safeArgs: TransactionDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        val transactionId = safeArgs.transactionId
        Toast.makeText(context, "show detail for transaction $transactionId", Toast.LENGTH_SHORT)
            .show()


        binding.categoryContainer.setOnClickListener {
            findNavController().navigate(R.id.action_select_category)
        }

        return binding.root
    }
}