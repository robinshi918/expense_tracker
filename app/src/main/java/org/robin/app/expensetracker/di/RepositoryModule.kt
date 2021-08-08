package org.robin.app.expensetracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.robin.app.expensetracker.data.Repository
import org.robin.app.expensetracker.data.RepositoryImpl

/**
 * Module to provide implementation instance of Repository interface.
 * So Hilt framework knows how to create an Repository instance.
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(repo: RepositoryImpl): Repository
}