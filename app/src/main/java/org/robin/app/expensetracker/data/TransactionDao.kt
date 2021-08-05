package org.robin.app.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    fun getAll(): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE transactionId = :id")
    fun findById(id: Int): List<Transaction>

    // TODO to implement
    /**
     * find all transactions for a certain month
     */
    /*@Query("SELECT * FROM `transaction`")
    fun findByDate(month: String): List<Transaction>*/

    @Insert
    fun insert(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)
}