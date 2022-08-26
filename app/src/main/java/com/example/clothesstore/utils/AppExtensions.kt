package com.example.clothesstore.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImageUsingUrl(imageUri: String) {
    if(imageUri.isNotEmpty()) {
        Picasso.get().load(imageUri).into(this)
    }
}