package org.robin.app.expensetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */
@Dao
interface ExchangeRateDao {

    @Query("SELECT * FROM `exchange_rate` WHERE date = :date")
    fun getRateByDate(date: String): Flow<ExchangeRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate: ExchangeRate)

    @Delete
    fun delete(rate: ExchangeRate)
}
