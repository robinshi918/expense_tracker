package org.robin.app.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */
@Entity(tableName = "exchange_rate")
data class ExchangeRate(
    @PrimaryKey @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "rate") var rate: Float,

    )
