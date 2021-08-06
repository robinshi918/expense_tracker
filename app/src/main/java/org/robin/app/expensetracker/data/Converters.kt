package org.robin.app.expensetracker.data

import androidx.room.TypeConverter
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 6/08/21
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}