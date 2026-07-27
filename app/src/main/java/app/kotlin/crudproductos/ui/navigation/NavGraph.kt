package app.kotlin.crudproductos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.kotlin.crudproductos.ui.screens.AddProductScreen
import app.kotlin.crudproductos.ui.screens.HomeScreen
import app.kotlin.crudproductos.ui.viewmodel.ProductViewModel


@Composable
fun NavGraph(
    productViewModel: ProductViewModel= viewModel()
) {

    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "home"
    ) {
        composable("home"){
            HomeScreen(productViewModel){
                navController.navigate("add")
            }
        }
        composable("add") {
            AddProductScreen(productViewModel){
                navController.navigate("home"){
                    popUpTo  (0)
                }
            }
        }
    }
}