package com.example.clothesstore.presentation.fragments.catalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.utils.loadImageUsingUrl

class CatalogueAdapter(products: MutableList<Product>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var products: List<Product>

    init {
        this.products = products.apply {
            products.add(0, Product(viewHolderType = Product.TITLE_SECTION, viewHolderTitle = "Catalogue"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            Product.TITLE_SECTION -> {
                return TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false))
            }
            Product.BODY_SECTION-> {
                return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
            }
            else -> {
                return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = products[position]
        when(holder){
            is TitleViewHolder -> {
                holder.tvTitle.text = row.viewHolderTitle
            }
            is ProductViewHolder -> {
                holder.apply {
                    tvProductName.text = row.name
                    tvProductPrice.text = row.price.toString()
                    ivProduct.loadImageUsingUrl(row.image ?: "")
                }
            }
        }

    }

    override fun getItemViewType(position: Int) = products[position].viewHolderType

    override fun getItemCount() = products.size

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal val tvProductName = itemView.findViewById<TextView>(R.id.tv_product_name)
        internal val tvProductPrice = itemView.findViewById<TextView>(R.id.tv_product_price)
        internal val ivProduct = itemView.findViewById<ImageView>(R.id.iv_product)
    }

    class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
    }

}