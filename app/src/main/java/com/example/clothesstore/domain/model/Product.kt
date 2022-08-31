package com.example.clothesstore.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    //General product info
    val name: String? = null,
    val image: String? = null,
    val price: Float = 0f,
    val stock: Int = 0,
    val category: String? = null,
    val oldPrice: Float = 0f,
    val productId: String? = null,
    //'viewHolderType' helps in distinguishing between the different viewHolder types.
    val viewHolderType: Int = 0,
    //As the name denotes its the title of the specific screen(i.e, could be Catalogue, Wishlist or Basket).
    val screenTitle: String? = null,
    //'emptyMessage' is used to display a message whenever the list(i.e, wishlist or basket) is empty.
    val emptyMessage: String? = null,
    //'isAddedToBasket' helps us in indicating whether a specific product was added to cart and still is present within the cart.
    val isAddedToBasket: Boolean = false,
    //'isWishListed' helps us in indicating whether a specific product was added to the wishlist by the user.
    val isWishListed: Boolean = false,
    //'cartQuantity' helps in keeping track of the quantity of the same/specific product.
    var cartQuantity: Int = 1
): Parcelable {
    companion object {
        //ViewHolder type's
        //TODO: Look into collapsing toolbar for title section, instead of it being part of recycler view
        const val TITLE_SECTION: Int = 1
        const val BODY_SECTION: Int = 2
        const val EMPTY_MSG_SECTION: Int = 3
    }
}
