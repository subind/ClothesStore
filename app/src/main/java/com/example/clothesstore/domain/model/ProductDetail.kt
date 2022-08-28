package com.example.clothesstore.domain.model

data class ProductDetail(
    val viewHolderType: Int = 0,
    val primaryInfo: ProductDetailPrimaryInfo? = null,
    val secondaryInfo: ProductDetailSecondaryInfo? = null
) {
    companion object {
        const val IMG_PRICE_AVAILABILITY_DETAIL_SECTION: Int = 1
        const val DESCRIPTION_DETAIL_SECTION: Int = 2
    }
}

data class ProductDetailPrimaryInfo(
    val image: String? = null,
    val price: Float = 0f,
    val oldPrice: Float = 0f,
    val stock: Int = 0
)

data class ProductDetailSecondaryInfo(
    val title: String? = null,
    val body: String? = null
)
