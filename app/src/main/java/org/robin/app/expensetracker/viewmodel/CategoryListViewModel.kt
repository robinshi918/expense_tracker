package org.robin.app.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.robin.app.expensetracker.data.AppDatabase
import org.robin.app.expensetracker.data.Category
import org.robin.app.expensetracker.data.Repository
import javax.inject.Inject

/**
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@HiltViewModel
class CategoryListViewModel @Inject internal constructor(
    repo: Repository
) : ViewModel() {
    val categoryList: LiveData<List<Category>> =
        repo.getCategoryList().asLiveData()
}
