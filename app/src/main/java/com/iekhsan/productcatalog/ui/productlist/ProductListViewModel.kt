package com.iekhsan.productcatalog.ui.productlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iekhsan.productcatalog.data.Product
import com.iekhsan.productcatalog.data.ProductListUiState
import com.iekhsan.productcatalog.data.RetrofitInstance
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    var uiState by mutableStateOf<ProductListUiState>(ProductListUiState.Loading)
        private set

    private var loadedProducts: List<Product> = emptyList()
    private var currentSkip = 0
    private val pageSize = 20
    private var totalProducts = 0
    private var isLoading = false
    private var isSearchActive = false

    init {
        loadMoreProducts()
    }

    fun loadMoreProducts() {
        if (isSearchActive) return
        if (isLoading) return
        if (loadedProducts.isNotEmpty() && currentSkip >= totalProducts) return

        isLoading = true
        if (loadedProducts.isEmpty()) {
            uiState = ProductListUiState.Loading
        }

        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getProducts(limit = pageSize, skip = currentSkip)
                loadedProducts = loadedProducts + result.products
                totalProducts = result.total
                currentSkip += pageSize

                uiState = if (loadedProducts.isEmpty()) {
                    ProductListUiState.Empty
                } else {
                    ProductListUiState.Success(loadedProducts)
                }
            } catch (e: Exception) {
                uiState = ProductListUiState.Error(e.message ?: "Something went wrong")
            }
            isLoading = false
        }
    }

    var searchQuery by mutableStateOf("")

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
    }

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            isSearchActive = false
            loadedProducts = emptyList()
            currentSkip = 0
            totalProducts = 0
            loadMoreProducts()
            return
        }

        isSearchActive = true

        viewModelScope.launch {
            uiState = ProductListUiState.Loading
            try {
                val result = RetrofitInstance.api.searchProducts(query)
                uiState = if (result.products.isEmpty()) {
                    ProductListUiState.Empty
                } else {
                    ProductListUiState.Success(result.products)
                }
            } catch (e: Exception) {
                uiState = ProductListUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

}