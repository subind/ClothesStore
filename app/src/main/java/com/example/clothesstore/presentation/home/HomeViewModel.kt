package com.example.clothesstore.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstore.data.network.model.Product
import com.example.clothesstore.domain.use_case.FetchProducts
import com.example.clothesstore.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val fetchProductsUseCase: FetchProducts): ViewModel() {

    private val _productsLiveData = MutableLiveData<Resource<List<Product>>>()
    val productsLiveData = _productsLiveData

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            val products =  fetchProductsUseCase.fetch()
            productsLiveData.postValue(products.value)
        }
    }

}