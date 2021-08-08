package org.robin.app.expensetracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.robin.app.expensetracker.api.ExchangeRateService
import javax.inject.Singleton

/**
 *  provider module to create singleton of exchange rate service instance.
 *
 * @author Robin Shi
 * @since 5/08/21
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideExchangeRateService(): ExchangeRateService {
        return ExchangeRateService.create()
    }
}
