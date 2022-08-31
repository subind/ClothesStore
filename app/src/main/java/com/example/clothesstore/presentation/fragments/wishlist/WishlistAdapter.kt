package com.example.clothesstore.presentation.fragments.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.utils.AppUtils.Companion.inflateViewIfLastElement
import com.example.clothesstore.utils.loadImageUsingDrawable
import com.example.clothesstore.utils.loadImageUsingUrl

class WishlistAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val wishList: MutableList<Product> = mutableListOf()
    var addToBasket: ((Product) -> Unit)? = null
    var productToBeRemoved: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Product.TITLE_SECTION -> {
                TitleViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false)
                )
            }
            Product.BODY_SECTION -> {
                WishListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_wish, parent, false)
                )
            }
            Product.EMPTY_MSG_SECTION -> {
                EmptyListMessageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_empty_list_msg, parent, false)
                )
            }
            else -> {
                WishListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_wish, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = wishList[position]
        when (holder) {
            is TitleViewHolder -> {
                holder.tvTitle.text = row.screenTitle
            }
            is WishListViewHolder -> {
                holder.apply {
                    ivProduct.loadImageUsingUrl(row.image ?: "")
                    tvProductName.text = row.name
                    tvPrice.text = row.price.toString()
                    tvQuantity.text = row.stock.toString()
                    ivAddToBasket.apply {
                        loadImageUsingDrawable(R.drawable.icon_active_basket)
                        visibility = View.VISIBLE
                        setOnClickListener {
                            addToBasket?.invoke(row)
                        }
                    }
                    viewDividerBottom.visibility = inflateViewIfLastElement(position, wishList.size)
                }
            }
            is EmptyListMessageViewHolder -> {
                holder.tvMessage.text = row.emptyMessage
            }
        }
    }

    /**
     * Remove the element from _wishListLiveData in HomeViewModel, &
     * observe the changes made on it in the WishlistFragment, then update the adapter with this new list
     */
    fun deleteItemAtPosition(position: Int) {
        val product = wishList[position]
        productToBeRemoved?.invoke(product)
    }

    fun updateWishList(wishes: List<Product>) {
        wishList.clear()
        wishList.addAll(wishes)
        notifyDataSetChanged()
    }

    override fun getItemCount() = wishList.size

    override fun getItemViewType(position: Int) = wishList[position].viewHolderType

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
    }

    class WishListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val ivProduct = itemView.findViewById<ImageView>(R.id.iv_product)
        internal val tvProductName = itemView.findViewById<TextView>(R.id.tv_product_name)
        internal val tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
        internal val tvQuantity = itemView.findViewById<TextView>(R.id.tv_quantity)
        internal val ivAddToBasket = itemView.findViewById<ImageView>(R.id.iv_add_to_basket)
        internal val viewDividerBottom = itemView.findViewById<View>(R.id.view_divider_bottom)
    }

    class EmptyListMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val tvMessage = itemView.findViewById<TextView>(R.id.tv_message)
    }

}



