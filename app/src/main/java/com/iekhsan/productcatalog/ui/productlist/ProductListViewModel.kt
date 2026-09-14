package com.iekhsan.productcatalog.ui.productlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iekhsan.productcatalog.data.Product
import com.iekhsan.productcatalog.data.RetrofitInstance
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    private var currentSkip = 0
    private val pageSize = 20
    private var totalProducts = 0
    private var isLoading = false

    init {
        loadMoreProducts()
    }

    fun loadMoreProducts() {
        if (isLoading) return
        if (products.isNotEmpty() && currentSkip >= totalProducts) return

        isLoading = true
        viewModelScope.launch {
            val result = RetrofitInstance.api.getProducts(limit = pageSize, skip = currentSkip)
            products = products + result.products
            totalProducts = result.total
            currentSkip += pageSize
            isLoading = false
        }
    }

}