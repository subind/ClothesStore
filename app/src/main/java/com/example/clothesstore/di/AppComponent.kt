package com.example.clothesstore.di

import android.content.Context
import com.example.clothesstore.di.modules.RetrofitModule
import com.example.clothesstore.di.modules.StoreRepositoryModule
import com.example.clothesstore.presentation.fragments.basket.BasketFragment
import com.example.clothesstore.presentation.fragments.catalogue.CatalogueFragment
import com.example.clothesstore.presentation.fragments.wishlist.WishlistFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StoreRepositoryModule::class, RetrofitModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: CatalogueFragment)
    fun inject(fragment: WishlistFragment)
    fun inject(fragment: BasketFragment)
}