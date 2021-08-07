package org.robin.app.expensetracker.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */

@RunWith(AndroidJUnit4::class)
class ExchangeRateDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var exchangeRateDao: ExchangeRateDao

    private val rate1 = ExchangeRate("2021-08-07", 1.426523f, "USD", "NZD")
    private val rate2 = ExchangeRate("2021-01-01", 1.626523f, "USD", "NZD")
    private val rate3 = ExchangeRate("2021-08-01", 1.526523f, "USD", "NZD")

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        exchangeRateDao = database.exchangeRateDao()

        // insert rates into db
        exchangeRateDao.insert(rate1)
        exchangeRateDao.insert(rate2)
        exchangeRateDao.insert(rate3)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test fun testGetRate() = runBlocking {
        var rate = exchangeRateDao.getRateByDate(rate1.date, "USD", "NZD").first()
        assert(rate == rate1)
    }

    @Test fun testAll() = runBlocking {
        val date = "2021-05-01"
        val rate = ExchangeRate(date, 1.526523f, "USD", "NZD")
        exchangeRateDao.delete(rate)

        var result = exchangeRateDao.getRateByDate(date, "USD", "NZD").first()
        assert(null == result)

        exchangeRateDao.insert(rate)
        result = exchangeRateDao.getRateByDate(date, "USD", "NZD").first()
        assert(rate == result)

        result = exchangeRateDao.getRateByDate(date, "USD", "CNY").first()
        assert(null == result)

        result = exchangeRateDao.getRateByDate(date, "CNY", "NZD").first()
        assert(null == result)
    }

}