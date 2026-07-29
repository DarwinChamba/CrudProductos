package app.kotlin.crudproductos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.kotlin.crudproductos.ui.screens.AddProductScreen
import app.kotlin.crudproductos.ui.screens.AuthScreen
import app.kotlin.crudproductos.ui.screens.HomeScreen
import app.kotlin.crudproductos.ui.screens.RegisterScreen
import app.kotlin.crudproductos.ui.screens.SplashRoute
import app.kotlin.crudproductos.ui.screens.SplashScreen
import app.kotlin.crudproductos.ui.viewmodel.ProductViewModel
import app.kotlin.crudproductos.util.Navigate


@Composable
fun NavGraph(
    productViewModel: ProductViewModel= viewModel()
) {

    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "splash"
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
        composable("splash") {
            SplashScreen{
                when(it){
                    SplashRoute.AUTH -> {
                        navController.navigate("auth"){
                            popUpTo(0)
                        }
                    }
                    SplashRoute.HOME -> {
                        navController.navigate("home"){
                            popUpTo(0)
                        }

                    }
                }

            }
        }
        composable("auth"){
                        AuthScreen {
                            when(it){
                                Navigate.REGISTER -> {
                                    navController.navigate("register")
                                }
                                Navigate.HOME -> {
                                    navController.navigate("home"){
                                        popUpTo(0)
                                    }
                                }
                            }
                        }
        }
        composable("register") {
            RegisterScreen{
                navController.navigate("home"){
                    popUpTo(0)
                }
            }
        }
    }
}