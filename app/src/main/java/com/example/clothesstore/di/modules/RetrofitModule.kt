package com.example.clothesstore.di.modules

import com.example.clothesstore.data.network.StoreApi
import com.example.clothesstore.utils.AppUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val retrofitClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(AppUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(retrofitClient)
            .build()
    }

    @Provides
    fun provideStoreApi(retrofit: Retrofit): StoreApi {
        return retrofit.create(StoreApi::class.java)
    }

}