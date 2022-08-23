package com.example.clothesstore.data.network.model

data class Product(
    val name: String,
    val image: String,
    val price: Float,
    val stock: Int,
    val category: String,
    val oldPrice: Float,
    val productId: String,
)
