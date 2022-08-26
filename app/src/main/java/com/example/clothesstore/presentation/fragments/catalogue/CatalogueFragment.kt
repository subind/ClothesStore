package com.example.clothesstore.presentation.fragments.catalogue

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.MyApplication
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.presentation.home.HomeViewModel
import com.example.clothesstore.utils.Resource
import javax.inject.Inject

class CatalogueFragment : Fragment() {

    private val TAG = "CatalogueFragment"

    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var rvProducts: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
        initObservers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    private fun initUi(view: View) {
        rvProducts = view.findViewById<RecyclerView>(R.id.rv_products)
    }

    private fun initRvProducts(products: List<Product>) {
        val adapter = CatalogueAdapter(products.toMutableList())
        val layoutManager = GridLayoutManager(activity, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    Product.TITLE_SECTION -> {
                        2
                    }
                    else -> {
                        1
                    }
                }
            }

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    Product.TITLE_SECTION -> {
                        2
                    }
                    else -> {
                        1
                    }
                }
            }
        }
        rvProducts.layoutManager = layoutManager
        rvProducts.adapter = adapter
    }

    private fun initObservers() {
        homeViewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.i(TAG, "Success: ${it.data?.size}")
                    initRvProducts(it.data ?: mutableListOf())
                }
                is Resource.Error -> {
                    Log.i(TAG, "Error: ${it.errorMessage}")
                }
                is Resource.Loading -> {
                    Log.i(TAG, "Loading: ${it.isLoading}")
                }
            }
        })
    }
}