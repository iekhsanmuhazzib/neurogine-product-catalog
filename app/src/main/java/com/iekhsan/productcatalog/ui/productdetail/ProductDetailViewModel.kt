package com.iekhsan.productcatalog.ui.productdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iekhsan.productcatalog.data.Product
import com.iekhsan.productcatalog.data.ProductListUiState
import com.iekhsan.productcatalog.data.RetrofitInstance
import kotlinx.coroutines.launch

sealed class ProductDetailUiState {
    object Loading : ProductDetailUiState()
    data class Error(val message: String) : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
}

class ProductDetailViewModel(private val productId: Int) : ViewModel() {

    var uiState by mutableStateOf<ProductDetailUiState>(ProductDetailUiState.Loading)
        private set

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            uiState = ProductDetailUiState.Loading
            try {
                val product = RetrofitInstance.api.getProductsInfo(productId)
                uiState = ProductDetailUiState.Success(product)
            } catch (e: Exception) {
                uiState = ProductDetailUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

}