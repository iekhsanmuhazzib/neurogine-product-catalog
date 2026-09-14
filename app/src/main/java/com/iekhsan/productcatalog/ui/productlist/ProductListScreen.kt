package com.iekhsan.productcatalog.ui.productlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel()
) {
    LazyColumn {
        items(viewModel.products) { product ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = product.title)
                Text(text = "$${product.price}")
            }
        }
    }
}