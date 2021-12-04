package org.robin.app.expensetracker.util

import android.content.Context
import com.google.common.truth.Truth.assertThat
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.robin.app.expensetracker.R
import java.text.SimpleDateFormat
import java.util.Calendar

@RunWith(MockitoJUnitRunner::class)
class UtilTest {
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

    @Mock
    private lateinit var mockContext: Context

    @Test()
    fun readStringFromContext_LocalisedString() {
        `when`(mockContext.getString(R.string.app_name))
            .thenReturn(FAKE_APP_NAME)

        val result = mockContext.getString(R.string.app_name)
        assertThat(result).isEqualTo(FAKE_APP_NAME)
    }

    @Test
    fun test_getGreetings_ReturnsValidIfUseridIsValid() {
        val mockService = Mockito.mock(DependentService::class.java)
        val myClass = MyClassUnderTest(mockService)

        `when`(mockService.getUserName(1)).thenReturn("Robin")
        assertThat(myClass.getGreetings(1)).isEqualTo("Hello Robin")

        `when`(mockService.getUserName(2)).thenReturn("Jack")
        assertThat(myClass.getGreetings(2)).isEqualTo("Hello Jack")
    }

    @Test
    fun test_getGreetings_ReturnsUserNotExistIfUseridIsInvalid() {
        val mockService = Mockito.mock(DependentService::class.java)
        val myClass = MyClassUnderTest(mockService)

        `when`(mockService.getUserName(-1)).thenReturn(null)
        assertThat(myClass.getGreetings(-1)).isEqualTo("User Not Exist")
    }
}

interface DependentService {
    /**
     * get user name by id. return null if user id is not found.
     */
    fun getUserName(userId: Int): String?
}

class MyClassUnderTest(private val service: DependentService) {
    fun getGreetings(userId: Int): String {
        val userName = service.getUserName(userId)
        return if (userName != null) {
            "Hello $userName"
        } else {
            "User Not Exist"
        }
    }
}



private const val FAKE_APP_NAME = "ExpenseTracker"
