package org.robin.app.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import org.robin.app.expensetracker.util.CATEGORY_DATA_FILENAME
import org.robin.app.expensetracker.util.DATABASE_NAME
import org.robin.app.expensetracker.worker.SeedDatabaseWorker

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@Database(
    entities = [Transaction::class, ExchangeRate::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun exchangeRateDao(): ExchangeRateDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private lateinit var applicationContext: Context

        fun getInstance(context: Context): AppDatabase {
            applicationContext = context
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * setup callback to populate the category list data as soon as DB is created.
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(SeedDatabaseWorker.KEY_FILE_NAME to CATEGORY_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                ).build()
        }
    }
}