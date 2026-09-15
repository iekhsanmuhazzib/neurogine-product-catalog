package com.iekhsan.productcatalog.data

sealed class ProductListUiState {
    object Loading : ProductListUiState()
    object Empty : ProductListUiState()
    data class Error(val message: String) : ProductListUiState()
    data class Success(val products: List<Product>) : ProductListUiState()
}