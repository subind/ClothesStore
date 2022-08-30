package com.example.clothesstore.utils

import android.content.Context
import android.view.View
import androidx.annotation.AttrRes

class AppUtils {

    companion object {
        const val BASE_URL = "https://api.npoint.io/"

        /**
         * Used for divider views, that helps in preventing super-imposing of views that leads to not ideal, thicker views
         */
        fun inflateViewIfLastElement(position: Int, listSize: Int): Int {
            return if(position == listSize-1){
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        internal fun Context.getColorFromAttr(@AttrRes attrColor: Int
        ): Int {
            val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
            val textColor = typedArray.getColor(0, 0)
            typedArray.recycle()
            return textColor
        }

    }
}