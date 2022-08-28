package com.example.clothesstore.presentation.fragments.product

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.roundToInt

class ProductDetailBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var rvProductDetails: RecyclerView
    private lateinit var ivClose: ImageView
    private lateinit var btnWishlist: Button
    private lateinit var product: Product
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
        btnWishlist = view.findViewById(R.id.btn_wishlist)
        btnWishlist.setOnClickListener(this)
        ivClose = view.findViewById(R.id.iv_close)
        ivClose.setOnClickListener(this)
        rvProductDetails = view.findViewById(R.id.rv_product_detail)
        product = args.navArgProductDetail
        initRvProductDetails()
    }

    private fun initRvProductDetails() {
        val adapter = ProductDetailAdapter(product)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvProductDetails.layoutManager = layoutManager
        rvProductDetails.adapter = adapter
    }

    override fun onClick(view: View?) {
        when (view) {
            ivClose -> {
                dismiss()
            }
            btnWishlist -> {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("key", product)
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            val screenHeight = resources.displayMetrics.heightPixels
            val desiredScreenHeightInInt = (screenHeight / (1.1)).roundToInt()
            behavior.peekHeight = desiredScreenHeightInInt
        }
    }
}