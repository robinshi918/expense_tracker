package org.robin.app.expensetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author Robin Shi
 * @since 7/08/21
 */
@Dao
interface CategoryDao {

    @Query("SELECT * FROM `category`")
    fun getAllCategory(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryList: List<Category>)

}