package com.iekhsan.productcatalog.ui.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.size

@Composable
fun ProductDetailScreen(productId: Int) {
    val viewModel: ProductDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ProductDetailViewModel(productId) as T
            }
        }
    )

    when (val state = viewModel.uiState) {
        is ProductDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ProductDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }

        is ProductDetailUiState.Success -> {
            val product = state.product
            Column(modifier = Modifier.padding(16.dp)) {
                LazyRow {
                    items(product.images) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = product.title,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(200.dp)
                        )
                    }
                }
                Text(text = product.title)
                Text(text = "$${product.price}")
                Text(text = "Rating: ${product.rating}")
                Text(text = product.description)
            }
        }
    }
}