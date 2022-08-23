package com.example.clothesstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clothesstore.data.network.StoreApi
import com.example.clothesstore.data.network.model.Product
import com.example.clothesstore.domain.repository.StoreRepository
import com.example.clothesstore.utils.Resource

class StoreRepositoryImpl(val storeApi: StoreApi) : StoreRepository {

    override suspend fun getStoreData(): LiveData<Resource<List<Product>>> {
        val productsLiveData = MutableLiveData<Resource<List<Product>>>()
        try {
            productsLiveData.postValue(Resource.Loading(true))
            val response = storeApi.getStoreData()
            productsLiveData.postValue(Resource.Loading(false))
            if (response.isSuccessful) {
                val products = response.body()?.products
                productsLiveData.postValue(Resource.Success(products))
            } else {
                productsLiveData.postValue(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            productsLiveData.postValue(Resource.Loading(false))
            productsLiveData.postValue(Resource.Error(e.toString()))
        }
        return productsLiveData
    }

}