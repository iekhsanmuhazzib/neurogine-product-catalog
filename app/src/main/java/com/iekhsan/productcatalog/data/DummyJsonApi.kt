package com.iekhsan.productcatalog.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyJsonApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int
    ): ProductInfo

    @GET("products/{id}")
    suspend fun getProductsInfo(
        @Path("id") id : Int
    ): Product
}