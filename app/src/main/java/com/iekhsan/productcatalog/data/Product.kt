package com.iekhsan.productcatalog.data

data class Product(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val images: List<String>
)