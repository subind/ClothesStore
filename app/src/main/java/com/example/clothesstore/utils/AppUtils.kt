package com.example.clothesstore.utils

import android.view.View
import com.example.clothesstore.domain.model.ProductDetail

class AppUtils {

    companion object {
        const val BASE_URL = "https://api.npoint.io/"

        fun inflateEmptyBlockIfLastElement(position: Int, productDetails: List<ProductDetail>): Int {
            return if(position == productDetails.size-1){
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    }
}