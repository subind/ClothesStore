package com.example.clothesstore.di.modules

import com.example.clothesstore.data.repository.StoreRepositoryImpl
import com.example.clothesstore.domain.repository.StoreRepository
import dagger.Binds
import dagger.Module

@Module
abstract class StoreRepositoryModule {

    @Binds
    abstract fun provideStoreRepository(storeRepo: StoreRepositoryImpl): StoreRepository
}