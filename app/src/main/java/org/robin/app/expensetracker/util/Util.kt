package org.robin.app.expensetracker.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Utility methods
 *
 * @author Robin Shi
 * @since 7/08/21
 */
class Util {

    companion object {

        fun calendar2ddmmyyyyString(calendar: Calendar): String {
            return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(calendar.time)
        }

        fun calendar2myyyString(calendar: Calendar): String {
            return SimpleDateFormat("MM/yyyy", Locale.ENGLISH).format(calendar.time)
        }

        /**
         * parse the parameter in format of "dd/MM/yyyy" and return a Calendar instance.
         * return null if parse failed.
         */
        fun ddmmyyyyString2Calendar(str: String): Calendar? {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            try {
                sdf.parse(str)?.let { cal.time = it }
            } catch (ex: ParseException) {
                ex.printStackTrace()
                return null
            }
            return cal
        }
    }
}