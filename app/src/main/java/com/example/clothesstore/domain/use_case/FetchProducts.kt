package com.example.clothesstore.domain.use_case

import androidx.lifecycle.LiveData
import com.example.clothesstore.data.network.model.Product
import com.example.clothesstore.domain.repository.StoreRepository
import com.example.clothesstore.utils.Resource

class FetchProducts(private val storeRepo: StoreRepository) {

    suspend fun fetch(): LiveData<Resource<List<Product>>> {
        return storeRepo.getStoreProducts()
    }

}