package com.example.clothesstore.data.network.model

import com.example.clothesstore.domain.model.Product

data class ProductDto(
    val name: String,
    val image: String,
    val price: Float,
    val stock: Int,
    val category: String,
    val oldPrice: Float,
    val productId: String,
) {
    fun toProduct(): Product {
        return Product(
            name = name,
            image = image,
            price = price,
            stock = stock,
            category = category,
            oldPrice = oldPrice,
            productId = productId,
            viewHolderType = Product.BODY_SECTION
        )
    }
}
