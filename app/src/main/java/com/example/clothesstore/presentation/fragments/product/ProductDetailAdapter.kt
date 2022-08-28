package com.example.clothesstore.presentation.fragments.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.domain.model.ProductDetail
import com.example.clothesstore.utils.AppUtils.Companion.inflateEmptyBlockIfLastElement
import com.example.clothesstore.utils.isAvailable
import com.example.clothesstore.utils.loadImageUsingUrl
import com.example.clothesstore.utils.toProductDetails

class ProductDetailAdapter(product: Product): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productDetails: List<ProductDetail>

    init {
        this.productDetails = product.toProductDetails()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ProductDetail.IMG_PRICE_AVAILABILITY_DETAIL_SECTION -> {
                ProductDetailPrimaryInfoViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_product_detail_primary, parent, false)
                )
            }
            ProductDetail.DESCRIPTION_DETAIL_SECTION -> {
                ProductDetailSecondaryInfoViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_product_detail_secondary, parent, false)
                )
            }
            else -> {
                ProductDetailPrimaryInfoViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_product_detail_primary, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = productDetails[position]
        when (holder) {
            is ProductDetailPrimaryInfoViewHolder -> {
                holder.apply {
                    ivProduct.loadImageUsingUrl(row.primaryInfo?.image ?: "")
                    tvPrice.text = row.primaryInfo?.price.toString()
                    tvOldPrice.text = row.primaryInfo?.oldPrice.toString()
                    tvAvailability.text = row.primaryInfo?.stock?.isAvailable()
                }
            }
            is ProductDetailSecondaryInfoViewHolder -> {
                holder.apply {
                    tvTitle.text = row.secondaryInfo?.title
                    tvDescription.text = row.secondaryInfo?.body
                    viewEmptyBlock.visibility = inflateEmptyBlockIfLastElement(position, productDetails)
                }
            }
        }
    }

    override fun getItemCount() = productDetails.size

    override fun getItemViewType(position: Int) = productDetails[position].viewHolderType

    class ProductDetailPrimaryInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal val ivProduct = itemView.findViewById<ImageView>(R.id.iv_product)
        internal val tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
        internal val tvOldPrice = itemView.findViewById<TextView>(R.id.tv_old_price)
        internal val tvAvailability = itemView.findViewById<TextView>(R.id.tv_availability)
    }

    class ProductDetailSecondaryInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        internal val tvDescription = itemView.findViewById<TextView>(R.id.tv_description)
        internal val viewEmptyBlock = itemView.findViewById<View>(R.id.view_empty_block)
    }

}