package com.example.clothesstore.domain.repository

import androidx.lifecycle.LiveData
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.utils.Resource

interface StoreRepository {

    suspend fun getStoreProducts(): LiveData<Resource<List<Product>>>

}