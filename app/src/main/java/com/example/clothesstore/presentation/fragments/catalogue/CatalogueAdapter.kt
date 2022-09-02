package com.example.clothesstore.presentation.fragments.catalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.utils.loadImageUsingUrl

class CatalogueAdapter(products: MutableList<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var products: List<Product>
    var onItemClick: ((Product) -> Unit)? = null

    init {
        this.products = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = products[position]
        (holder as ProductViewHolder).apply {
            ivProduct.loadImageUsingUrl(row.image ?: "")
            tvProductName.text = row.name
            tvProductPrice.text = row.price.toString()
            cvProduct.setOnClickListener {
                onItemClick?.invoke(row)
            }
        }
    }

    override fun getItemViewType(position: Int) = products[position].viewHolderType

    override fun getItemCount() = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val tvProductName = itemView.findViewById<TextView>(R.id.tv_product_name)
        internal val tvProductPrice = itemView.findViewById<TextView>(R.id.tv_product_price)
        internal val ivProduct = itemView.findViewById<ImageView>(R.id.iv_product)
        internal val cvProduct = itemView.findViewById<CardView>(R.id.cv_product)
    }

}