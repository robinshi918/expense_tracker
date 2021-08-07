package org.robin.app.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
