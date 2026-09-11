package com.iekhsan.productcatalog.data

data class ProductInfo(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)