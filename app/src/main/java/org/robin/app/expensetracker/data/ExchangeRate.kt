package org.robin.app.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Save historical exchange rates in database as local cache.
 *
 * @author Robin Shi
 * @since 7/08/21
 */
@Entity(tableName = "exchange_rate", primaryKeys = ["date", "source", "target"])
data class ExchangeRate(
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "rate") var rate: Float,
    @ColumnInfo(name = "source") var source: String,
    @ColumnInfo(name = "target") var target: String,
)
