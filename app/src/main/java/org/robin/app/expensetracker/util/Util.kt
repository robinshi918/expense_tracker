package org.robin.app.expensetracker.util

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */
class Util {

    companion object {
        fun calendar2String(calendar: Calendar): String {
            return SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
        }

        fun calendar2StringWithoutDay(calendar: Calendar): String {
            return SimpleDateFormat("MM/yyyy").format(calendar.time)
        }
    }
}