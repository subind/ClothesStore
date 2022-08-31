package com.example.clothesstore.presentation.fragments.basket

import android.content.Context
import android.os.Bundle
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

class BasketFragment: Fragment() {

    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var rvBasket: RecyclerView
    private var adapter: BasketAdapter? = null
    private var swipeHelper: SwipeToDeleteHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
        initRvBasket()
        initObservers()
        initCallBacks()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    private fun initUi(view: View) {
        rvBasket = view.findViewById<RecyclerView>(R.id.rv_basket)
    }

    private fun initObservers() {
        homeViewModel.basketListLiveData.observe(viewLifecycleOwner, Observer { product ->
            product?.let { adapter?.updateBasketList(it) }
        })
    }

    private fun initRvBasket() {
        adapter = BasketAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvBasket.layoutManager = layoutManager
        rvBasket.adapter = adapter

        initSwipeHelper()
    }

    private fun initSwipeHelper() {
        swipeHelper = object : SwipeToDeleteHelper(context, rvBasket) {
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
                            adapter?.deleteItemAtPosition(pos)
                        }
                    }
                ))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHelper as SwipeToDeleteHelper)
        itemTouchHelper.attachToRecyclerView(rvBasket)
    }

    private fun initCallBacks() {
        adapter?.productToBeRemoved = {
            homeViewModel.deleteFromBasketLiveData(it)
        }
    }

}