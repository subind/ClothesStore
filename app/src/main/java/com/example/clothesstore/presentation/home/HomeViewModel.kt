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

class HomeViewModel @Inject constructor(private val fetchProductsUseCase: FetchProducts): ViewModel() {

    private val _productsLiveData = MutableLiveData<Resource<List<Product>>>()
    val productsLiveData = _productsLiveData
    private lateinit var products: LiveData<Resource<List<Product>>>

    init {
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

    /*override fun onCleared() {
        super.onCleared()
        products.removeObserver()
    }*/

}