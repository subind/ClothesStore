package com.example.clothesstore.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.domain.use_case.FetchProducts
import com.example.clothesstore.utils.Resource
import com.example.clothesstore.utils.ifDuplicatePresentThenDecrementCartQuantityElseRemove
import com.example.clothesstore.utils.ifDuplicatePresentThenIncrementCartQuantityElseAdd
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val fetchProductsUseCase: FetchProducts): ViewModel() {

    private val TAG = "HomeViewModel"
    private val _productsLiveData = MutableLiveData<Resource<List<Product>>>()
    val productsLiveData = _productsLiveData

    private val _wishListLiveData = MutableLiveData<List<Product>>(emptyList())
    val wishListLiveData = _wishListLiveData

    private val _basketListLiveData = MutableLiveData<List<Product>>(emptyList())
    val basketListLiveData = _basketListLiveData

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            fetchProductsUseCase.fetch().observeForever {
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

    //Todo: Make sure to check the cart quantity doesn't exceed the available stock
    fun addToBasketLiveData(product: Product) {
        _basketListLiveData.value = _basketListLiveData.value?.toMutableList()?.apply {
            ifDuplicatePresentThenIncrementCartQuantityElseAdd(product)
        }?.toList()
    }

    fun deleteFromBasketLiveData(product: Product) {
        _basketListLiveData.value = _basketListLiveData.value?.toMutableList()?.apply {
            ifDuplicatePresentThenDecrementCartQuantityElseRemove(product)
        }?.toList()
    }


}