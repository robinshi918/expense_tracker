package org.robin.app.expensetracker.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */
class Util {

    companion object {
        private const val TAG = "Util"
        fun calendar2String(calendar: Calendar): String {
            return SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
        }

        fun calendar2StringWithoutDay(calendar: Calendar): String {
            return SimpleDateFormat("MM/yyyy").format(calendar.time)
        }

        /**
         * pass a dd/MM/yyyy date string and return a Calendar instance
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