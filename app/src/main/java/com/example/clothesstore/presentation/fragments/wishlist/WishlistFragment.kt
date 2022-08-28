package com.example.clothesstore.presentation.fragments.wishlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.MyApplication
import com.example.clothesstore.R
import com.example.clothesstore.domain.model.Product
import com.example.clothesstore.presentation.home.HomeViewModel
import javax.inject.Inject

class WishlistFragment: Fragment() {

    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var rvWishes: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
        initObservers()
        initRvWishes(homeViewModel.wishedProducts)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    private fun initUi(view: View) {
        rvWishes = view.findViewById<RecyclerView>(R.id.rv_wishes)
    }

    private fun initObservers() {
        homeViewModel.wishListLiveData.observe(viewLifecycleOwner, Observer {
            initRvWishes(it)
        })
    }

    private fun initRvWishes(wishes: List<Product>) {
        val adapter = WishlistAdapter(wishes.toMutableList())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvWishes.layoutManager = layoutManager
        rvWishes.adapter = adapter
        adapter.onItemClick = {
            homeViewModel.setWishListLiveData(it)
        }
    }

}