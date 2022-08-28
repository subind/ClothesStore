package com.example.clothesstore.utils

import android.widget.ImageView
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.domain.model.ProductDetail
import com.example.clothesstore.domain.model.ProductDetailPrimaryInfo
import com.example.clothesstore.domain.model.ProductDetailSecondaryInfo
import com.squareup.picasso.Picasso

fun ImageView.loadImageUsingUrl(imageUri: String) {
    if (imageUri.isNotEmpty()) {
        Picasso.get().load(imageUri).into(this)
    }
}

fun Int.isAvailable(): String {
    return if(this > 0){
        "In Stock"
    } else {
        "Out Of Stock"
    }
}

fun Product.toProductDetails(): List<ProductDetail> {
    val productDetails = mutableListOf<ProductDetail>()
    productDetails.add(
        ProductDetail(
            viewHolderType = ProductDetail.IMG_PRICE_AVAILABILITY_DETAIL_SECTION,
            primaryInfo = ProductDetailPrimaryInfo(
                image = this.image,
                price = this.price,
                oldPrice = this.oldPrice,
                stock = this.stock
            )
        )
    )
    productDetails.add(
        ProductDetail(
            viewHolderType = ProductDetail.DESCRIPTION_DETAIL_SECTION,
            secondaryInfo = ProductDetailSecondaryInfo(
                title = "NAME",
                body = this.name
            )
        )
    )
    productDetails.add(
        ProductDetail(
            viewHolderType = ProductDetail.DESCRIPTION_DETAIL_SECTION,
            secondaryInfo = ProductDetailSecondaryInfo(
                title = "CATEGORY",
                body = this.category
            )
        )
    )
    productDetails.add(
        ProductDetail(
            viewHolderType = ProductDetail.DESCRIPTION_DETAIL_SECTION,
            secondaryInfo = ProductDetailSecondaryInfo(
                title = "AMOUNT IN STOCK",
                body = this.stock.toString()
            )
        )
    )
    return productDetails
}