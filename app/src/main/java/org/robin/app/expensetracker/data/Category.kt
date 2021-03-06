package org.robin.app.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * category table.
 * TODO: use category id as primary and foreign key with 'transaction' table
 */
@Entity(tableName = "category")
data class Category(
    @PrimaryKey @ColumnInfo(name = "name") val name: String
) {
    companion object {
        const val INVALID_CATEGORY_ID = -1
    }

    override fun toString(): String {
        return "Category(name='$name')"
    }


}
