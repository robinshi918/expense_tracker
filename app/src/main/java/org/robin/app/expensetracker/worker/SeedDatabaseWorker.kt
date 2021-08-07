package org.robin.app.expensetracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.robin.app.expensetracker.data.AppDatabase
import org.robin.app.expensetracker.data.Category

/**
 *
 * This worker class is to initialise 'category' table with hardcoded values
 * when database is created.
 * @author Robin Shi
 * @since 7/08/21
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.d(TAG, "start initialising category table")
        try {
            val fileName = inputData.getString(KEY_FILE_NAME)
            if (fileName != null) {
                applicationContext.assets.open(fileName).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val categoryType = object : TypeToken<List<Category>>() {}.type
                        val categoryList: List<Category> = Gson().fromJson(jsonReader, categoryType)
                        val database = AppDatabase.getInstance(applicationContext)
                        database.categoryDao().insertAll(categoryList)
                        Log.d(TAG, "finish initialising category table")
                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()

        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILE_NAME = "CATEGORY_DATA_FILENAME"
    }

}