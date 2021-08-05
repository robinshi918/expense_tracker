package org.robin.app.expensetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    fun getAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE transactionId = :id")
    fun findById(id: Int): Flow<List<Transaction>>

    // TODO to implement
    /**
     * find all transactions for a certain month
     */
    /*@Query("SELECT * FROM `transaction`")
    fun findByDate(month: String): Flow<List<Transaction>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)
}