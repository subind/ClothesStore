package com.example.clothesstore.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.domain.use_case.FetchProducts
import com.example.clothesstore.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val fetchProductsUseCase: FetchProducts): ViewModel() {

    private lateinit var productsInCatalogue: LiveData<Resource<List<Product>>>

    private val _productsLiveData = MutableLiveData<Resource<List<Product>>>()
    val productsLiveData = _productsLiveData

    private val _wishListLiveData = MutableLiveData<List<Product>>(emptyList())
    val wishListLiveData = _wishListLiveData

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            productsInCatalogue =  fetchProductsUseCase.fetch()
            productsInCatalogue.observeForever {
                productsLiveData.value = it
            }
        }
    }

    fun addToWishListLiveData(wishedProduct: Product) {
        _wishListLiveData.value = _wishListLiveData.value?.toMutableList()?.apply {
            add(wishedProduct)
        }?.toList()
    }

    fun deleteFromWishListLiveData(wishedProduct: Product) {
        _wishListLiveData.value = _wishListLiveData.value?.toMutableList()?.apply {
            remove(wishedProduct)
        }?.toList()
    }

}