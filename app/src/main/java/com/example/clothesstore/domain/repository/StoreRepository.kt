package com.example.clothesstore.domain.repository

import androidx.lifecycle.LiveData
import com.example.clothesstore.data.network.model.Product
import com.example.clothesstore.data.network.model.Store
import com.example.clothesstore.utils.Resource
import retrofit2.Response

interface StoreRepository {

    suspend fun getStoreData(): LiveData<Resource<List<Product>>>

}