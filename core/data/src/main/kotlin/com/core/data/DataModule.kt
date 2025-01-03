package com.core.data

import com.mfs.core.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsFoodRepository(
        topicsRepository: FoodDataSource,
    ): FoodRepository

}
