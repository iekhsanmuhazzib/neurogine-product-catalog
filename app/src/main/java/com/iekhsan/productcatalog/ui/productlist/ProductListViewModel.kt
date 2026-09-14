package com.iekhsan.productcatalog.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iekhsan.productcatalog.data.Product
import com.iekhsan.productcatalog.data.RetrofitInstance
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ProductListViewModel : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val result = RetrofitInstance.api.getProducts(limit = 20, skip = 0)
            products = result.products
        }
    }

}