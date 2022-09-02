package com.example.clothesstore.presentation.fragments.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.utils.AppUtils
import com.example.clothesstore.utils.loadImageUsingUrl

class BasketAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val basketList: MutableList<Product> = mutableListOf()
    var productToBeRemoved: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BasketListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wish_basket, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = basketList[position]
        (holder as BasketListViewHolder).apply {
            ivProduct.loadImageUsingUrl(row.image ?: "")
            tvProductName.text = row.name
            tvPrice.text = row.price.toString()
            tvQuantity.apply {
                visibility = View.VISIBLE
                text = row.cartQuantity.toString()
            }
            ivAddToBasket.apply {
                visibility = View.GONE
            }
            viewDividerBottom.visibility =
                AppUtils.inflateViewIfLastElement(position, basketList.size)
        }
    }

    fun deleteItemAtPosition(position: Int) {
        val product = basketList[position]
        productToBeRemoved?.invoke(product)
    }

    fun updateBasketList(products: List<Product>) {
        basketList.clear()
        basketList.addAll(products)
        notifyDataSetChanged()
    }

    override fun getItemCount() = basketList.size

    override fun getItemViewType(position: Int) = basketList[position].viewHolderType

    class BasketListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val ivProduct = itemView.findViewById<ImageView>(R.id.iv_product)
        internal val tvProductName = itemView.findViewById<TextView>(R.id.tv_product_name)
        internal val tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
        internal val tvQuantity = itemView.findViewById<TextView>(R.id.tv_quantity)
        internal val ivAddToBasket = itemView.findViewById<ImageView>(R.id.iv_add_to_basket)
        internal val viewDividerBottom = itemView.findViewById<View>(R.id.view_divider_bottom)
    }

}