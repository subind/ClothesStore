package com.example.clothesstore.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val name: String? = null,
    val image: String? = null,
    val price: Float = 0f,
    val stock: Int = 0,
    val category: String? = null,
    val oldPrice: Float = 0f,
    val productId: String? = null,
    val viewHolderType: Int = 0,
    val viewHolderTitle: String? = null,
): Parcelable {
    companion object {
        const val TITLE_SECTION: Int = 1
        const val BODY_SECTION: Int = 2
    }
}
