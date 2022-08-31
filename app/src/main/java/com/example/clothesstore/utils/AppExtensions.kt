package com.example.clothesstore.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.domain.model.ProductDetail
import com.example.clothesstore.domain.model.ProductDetailPrimaryInfo
import com.example.clothesstore.domain.model.ProductDetailSecondaryInfo
import com.squareup.picasso.Picasso

@SuppressLint("ObsoleteSdkInt")
fun ImageView.loadImageUsingDrawable(drawable: Int) {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        this.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable));
    } else {
        this.background = ContextCompat.getDrawable(context, drawable);
    }
}

fun ImageView.loadImageUsingUrl(imageUri: String) {
    if (imageUri.isNotEmpty()) {
        Picasso.get().load(imageUri).into(this)
    }
}

fun Int.isAvailable(): String {
    return if (this > 0) {
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

fun MutableList<Product>.ifDuplicatePresentThenIncrementCartQuantityElseAdd(productToBeAdded: Product) {
    var notFoundSameProduct = true
    this.forEach { productInCart ->
        if (productInCart.productId.equals(productToBeAdded.productId)) {
            productInCart.cartQuantity += 1
            notFoundSameProduct = false
        }
    }
    if (notFoundSameProduct || this.isEmpty()) {
        this.add(productToBeAdded)
    }
}

fun MutableList<Product>.ifDuplicatePresentThenDecrementCartQuantityElseRemove(productToBeRemoved: Product) {
    var notFoundSameProduct = true
    this.forEach { productInCart ->
        if (productInCart.productId.equals(productToBeRemoved.productId) && productInCart.cartQuantity > 1) {
            productInCart.cartQuantity -= 1
            notFoundSameProduct = false
        }
    }
    if (notFoundSameProduct) {
        this.remove(productToBeRemoved)
    }
}

