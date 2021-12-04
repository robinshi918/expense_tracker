package org.robin.app.expensetracker

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Q])
class RobolectricUnitTest {

    @Test
    fun test_readStringFromContext_LocalizedString() {
        val mockContext: Context = ApplicationProvider.getApplicationContext()
        val textAppName: String = mockContext.getString(R.string.app_name)

        // ...then the result should be the expected one.
        assertThat(textAppName).isEqualTo("Expense Tracker")

        val textValue: String = mockContext.getString(R.string.title_value)
        assertThat(textValue).isEqualTo("Value")
    }

}