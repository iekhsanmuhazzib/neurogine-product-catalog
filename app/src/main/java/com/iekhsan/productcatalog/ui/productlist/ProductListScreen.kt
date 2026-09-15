package com.iekhsan.productcatalog.ui.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iekhsan.productcatalog.data.ProductListUiState
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel(),
    onProductClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem >= totalItems - 5
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.loadMoreProducts()
        }
    }

    LaunchedEffect(viewModel.searchQuery) {
        delay(500)
        viewModel.searchProducts(viewModel.searchQuery)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            label = { Text("Search products") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        when (val state = viewModel.uiState) {
            is ProductListUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ProductListUiState.Empty -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No products found")
                }
            }

            is ProductListUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        Text(text = state.message)
                        Button(onClick = { viewModel.loadMoreProducts() }) {
                            Text(text = "Retry")
                        }
                    }
                }
            }

            is ProductListUiState.Success -> {
                LazyColumn(state = listState) {
                    items(state.products) { product ->
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable { onProductClick(product.id) }
                        ) {
                            AsyncImage(
                                model = product.thumbnail,
                                contentDescription = product.title,
                                modifier = Modifier.size(80.dp)
                            )
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(text = product.title)
                                Text(text = "$${product.price}")
                            }
                        }
                    }
                }
            }
        }
    }
}