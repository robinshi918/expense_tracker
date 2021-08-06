package org.robin.app.expensetracker.data

data class Category(
    val id: Int,
    val name: String
) {
    companion object {
        const val INVALID_CATEGORY_ID = -1
    }
}
