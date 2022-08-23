package com.example.clothesstore.data.network

import com.example.clothesstore.data.network.model.Store
import retrofit2.Response
import retrofit2.http.GET

interface StoreApi {

    @GET("0f78766a6d68832d309d")
    suspend fun getStoreData(): Response<Store>

}