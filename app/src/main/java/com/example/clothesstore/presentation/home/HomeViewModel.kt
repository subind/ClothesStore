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

    private lateinit var products: LiveData<Resource<List<Product>>>

    private val _productsLiveData = MutableLiveData<Resource<List<Product>>>()
    val productsLiveData = _productsLiveData

    private val _wishListLiveData = MutableLiveData<List<Product>>()
    val wishListLiveData = _wishListLiveData

    var wishedProducts = mutableListOf<Product>()

    init {
        //wishedProducts.clear()
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            products =  fetchProductsUseCase.fetch()
            products.observeForever {
                productsLiveData.value = it
            }
        }
    }

    fun setWishListLiveData(wishedProduct: Product) {
        this.wishedProducts.add(wishedProduct)
        _wishListLiveData.value = wishedProducts
    }

}