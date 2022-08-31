package com.example.clothesstore.presentation.fragments.wishlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstore.MyApplication
import com.example.clothesstore.R
import com.example.clothesstore.presentation.home.HomeViewModel
import com.example.clothesstore.utils.AppUtils.Companion.getColorFromAttr
import com.example.clothesstore.utils.SwipeToDeleteHelper
import javax.inject.Inject

class WishlistFragment: Fragment() {

    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var rvWishes: RecyclerView
    private val TAG = "WishlistFragment"
    private var swipeHelper: SwipeToDeleteHelper? = null
    private var adapter: WishlistAdapter? = null

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
        initRvWishes()
        initCallBacks()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    private fun initUi(view: View) {
        rvWishes = view.findViewById<RecyclerView>(R.id.rv_wishes)
    }

    private fun initObservers() {
        homeViewModel.wishListLiveData.observe(viewLifecycleOwner, Observer { product ->
            product?.let { adapter?.updateWishList(it) }
        })
    }

    private fun initRvWishes() {
        adapter = WishlistAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvWishes.layoutManager = layoutManager
        rvWishes.adapter = adapter

        initSwipeHelper()
    }

    private fun initSwipeHelper() {
        swipeHelper = object : SwipeToDeleteHelper(context, rvWishes) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                underlayButtons?.add(UnderlayButton(
                    context = context!!,
                    imageResId = R.drawable.icon_delete,
                    color = context!!.getColorFromAttr(androidx.appcompat.R.attr.colorPrimary),
                    clickListener = object : UnderlayButtonClickListener {
                        override fun onClick(pos: Int) {
                            Log.i(TAG, "onClick: Deleted")
                            adapter?.deleteItemAtPosition(pos)
                        }
                    }
                ))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHelper as SwipeToDeleteHelper)
        itemTouchHelper.attachToRecyclerView(rvWishes)
    }

    private fun initCallBacks() {
        adapter?.addToBasket = {
            homeViewModel.addToBasketLiveData(it)
            //Delete the item Iin wishlist that was added to cart
            adapter?.deleteItemAtPosition(it)
        }

        adapter?.productToBeRemoved = {
            homeViewModel.deleteFromWishListLiveData(it)
        }
    }

}