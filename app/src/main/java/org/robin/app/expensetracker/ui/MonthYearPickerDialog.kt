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
 * Note: The original implementation of this class comes from StackOverflow:
 * https://stackoverflow.com/questions/21321789/android-datepicker-change-to-only-month-and-year
 * It supports a DatePicker with only Month and Year.
 *
 * Further customisation has been made by me:
 * 1. added an optional Day Picker, which is configurable
 *
 * @author Robin Shi
 * @since 6/08/21
 */
class MyDatePickerDialog constructor() : DialogFragment() {

    private var hasDayPicker: Boolean = true
    private var year = 1
    private var month = 0
    private var day = 1
    private var hasUserInput = false
    private var listener: OnDateSetListener? = null

    constructor(hasDayPicker: Boolean) : this() {
        this.hasDayPicker = hasDayPicker
    }

    constructor(hasDayPicker: Boolean, year: Int, month: Int, day: Int) : this(hasDayPicker) {
        this.year = year
        this.month = month
        this.day = day
        hasUserInput = true
    }

    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater


        val dialog = inflater.inflate(R.layout.date_picker_dialog, null)
        val monthPicker = dialog.findViewById<View>(R.id.picker_month) as NumberPicker
        val yearPicker = dialog.findViewById<View>(R.id.picker_year) as NumberPicker
        val dayPicker = dialog.findViewById<View>(R.id.picker_day) as NumberPicker

        dayPicker.visibility = if (hasDayPicker) View.VISIBLE else View.GONE

        val cal = if (!hasUserInput) {
            Calendar.getInstance()
        } else {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
        }

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = cal[Calendar.MONTH] + 1

        val year = cal[Calendar.YEAR]
        yearPicker.minValue = year
        yearPicker.maxValue = MAX_YEAR
        yearPicker.value = year

        if (hasDayPicker) {
            /*
             TODO() actual number of days in a month varies according to month.
             Day range needs to update dynamically
             For now, hardcode the value 1 - 31
             */
            dayPicker.minValue = 1
            dayPicker.maxValue = 31
            dayPicker.value = cal[Calendar.DAY_OF_MONTH]
        } else {
            dayPicker.value = 15
        }

        builder.setView(dialog) // Add action buttons
            .setPositiveButton(
                R.string.ok
            ) { _: DialogInterface?, _: Int ->
                listener?.onDateSet(
                    null,
                    yearPicker.value,
                    monthPicker.value - 1,
                    if (hasDayPicker) dayPicker.value else 1
                )
            }
            .setNegativeButton(R.string.cancel) { _, _ -> this@MyDatePickerDialog.dialog?.cancel() }
        return builder.create()
    }

    companion object {
        private const val MAX_YEAR = 2099
    }
}