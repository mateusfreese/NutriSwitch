package com.mfs.core.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesFoodDao(database: NutriSwitchDatabase): FoodDao {
        return database.getFoodDao()
    }

    @Provides
    fun providesFoodDatabase(
        @ApplicationContext applicationContext: Context
    ): NutriSwitchDatabase {
        return NutriSwitchDatabase.build(applicationContext)
    }
}