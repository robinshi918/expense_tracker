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

    @Query("SELECT * FROM `exchange_rate` WHERE date = :date AND source = :source AND target = :target")
    fun getRateByDate(date: String, source: String, target: String): Flow<ExchangeRate>

    @Query("SELECT count(*) FROM `exchange_rate` WHERE date = :date AND source = :source AND target = :target")
    fun hasRate(date: String, source: String, target: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate: ExchangeRate)

    @Delete
    fun delete(rate: ExchangeRate)
}
