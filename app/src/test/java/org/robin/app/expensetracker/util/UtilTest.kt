package org.robin.app.expensetracker.util

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 8/08/21
 */
class UtilTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun yyyymmdd2Calendar_test() {
        val cal1 = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val year1 = cal1[Calendar.YEAR]
        val month1 = cal1[Calendar.MONTH]
        val day1 = cal1[Calendar.DAY_OF_MONTH]

        val str = sdf.format(cal1.time)

        val cal2 = Util.ddmmyyyyString2Calendar(str)!!
        val year2 = cal2[Calendar.YEAR]
        val month2 = cal2[Calendar.MONTH]
        val day2 = cal2[Calendar.DAY_OF_MONTH]

        Assert.assertEquals(year1, year2)
        Assert.assertEquals(month1, month2)
        Assert.assertEquals(day1, day2)
    }



}

