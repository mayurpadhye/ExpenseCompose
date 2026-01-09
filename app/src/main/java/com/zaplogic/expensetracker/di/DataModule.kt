package com.zaplogic.expensetracker.di

import android.content.Context
import android.content.SharedPreferences
import com.zaplogic.expensetracker.domain.shared_prefernce.PreferenceManager
import com.zaplogic.expensetracker.data.repositoryImpl.PreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(
        @ApplicationContext context: Context
    ) : SharedPreferences {
        return context.getSharedPreferences("ExpenseTracker",Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesPreferenceRepository(
        sharedPreferences: SharedPreferences
    ) : PreferenceManager{
        return PreferenceRepositoryImpl(sharedPreferences)

    }

}