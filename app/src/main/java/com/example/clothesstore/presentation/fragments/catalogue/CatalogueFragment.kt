package com.example.clothesstore.presentation.fragments.catalogue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.clothesstore.R
import com.example.clothesstore.presentation.home.HomeViewModel
import com.example.clothesstore.utils.Resource

class CatalogueFragment: Fragment() {

    private val TAG = "CatalogueFragment"
    lateinit var homeViewModel: HomeViewModel

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
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initObservers()
    }

    private fun initObservers() {
        homeViewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Log.i(TAG, "Success: ${it.data?.size}")
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