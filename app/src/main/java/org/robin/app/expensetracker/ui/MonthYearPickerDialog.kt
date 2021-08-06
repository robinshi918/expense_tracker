package org.robin.app.expensetracker.ui

import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.robin.app.expensetracker.R
import java.util.*

/**
 * @author Robin Shi
 * @since 6/08/21
 */
class MonthYearPickerDialog : DialogFragment() {

    private var listener: OnDateSetListener? = null
    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        val cal = Calendar.getInstance()
        val dialog = inflater.inflate(R.layout.date_picker_dialog, null)
        val monthPicker = dialog.findViewById<View>(R.id.picker_month) as NumberPicker
        val yearPicker = dialog.findViewById<View>(R.id.picker_year) as NumberPicker
        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.value = cal[Calendar.MONTH] + 1
        val year = cal[Calendar.YEAR]
        yearPicker.minValue = year
        yearPicker.maxValue = MAX_YEAR
        yearPicker.value = year
        builder.setView(dialog) // Add action buttons
            .setPositiveButton(
                R.string.ok
            ) { _: DialogInterface?, _: Int ->
                listener?.onDateSet(
                    null,
                    yearPicker.value,
                    monthPicker.value,
                    0
                )
            }
            .setNegativeButton(R.string.cancel) { _, _ -> this@MonthYearPickerDialog.dialog?.cancel() }
        return builder.create()
    }

    companion object {
        private const val MAX_YEAR = 2099
    }
}