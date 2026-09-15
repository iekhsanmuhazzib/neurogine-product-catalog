package com.iekhsan.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iekhsan.productcatalog.ui.theme.ProductCatalogTheme
import com.iekhsan.productcatalog.ui.productlist.ProductListScreen
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.iekhsan.productcatalog.ui.productdetail.ProductDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductCatalogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "productList",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("productList") {
                            ProductListScreen(
                                onProductClick = { productId ->
                                    navController.navigate("productDetail/$productId")
                                }
                            )
                        }

                        composable(
                            route = "productDetail/{productId}",
                            arguments = listOf(navArgument("productId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                            ProductDetailScreen(productId = productId)
                        }
                    }
                }
            }
        }
    }
}