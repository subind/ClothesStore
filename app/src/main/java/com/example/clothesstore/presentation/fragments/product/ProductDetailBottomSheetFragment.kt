package com.example.clothesstore.presentation.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductDetailBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var rvProductDetails: RecyclerView
    private val args by navArgs<ProductDetailBottomSheetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_bottom_sheet_product_detail_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
    }

    private fun initUi(view: View) {
        rvProductDetails = view.findViewById(R.id.rv_product_detail)
        initRvProductDetails(args.navArgProductDetail)
    }

    private fun initRvProductDetails(product: Product) {
        val adapter = ProductDetailAdapter(product)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvProductDetails.layoutManager = layoutManager
        rvProductDetails.adapter = adapter
    }

}