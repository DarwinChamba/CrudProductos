package app.kotlin.crudproductos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.kotlin.crudproductos.ui.components.ButtonNavigate
import app.kotlin.crudproductos.ui.components.CardProduct
import app.kotlin.crudproductos.ui.components.DeleteDialog
import app.kotlin.crudproductos.ui.viewmodel.ProductViewModel
import app.kotlin.crudproductos.util.ProductState


@Composable
fun HomeScreen(
    productViewModel: ProductViewModel,
    navigate: () -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var idProduct by rememberSaveable { mutableStateOf("") }
    val listProduct by productViewModel.listProduct.collectAsState()
    DeleteDialog(showDialog, aceptar = {
        productViewModel.deleteProduct(idProduct)
        showDialog = false
    }, cancelar = { showDialog = false })

    Scaffold(
        floatingActionButton = {
            ButtonNavigate(navigate = navigate)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            when (val result = listProduct) {
                is ProductState.Error -> {

                }

                ProductState.Idle -> {}
                ProductState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ProductState.Success -> {
                    LazyColumn() {
                        items(result.data) { product ->
                            CardProduct(product, delete = {
                                idProduct = product.id
                                showDialog = true
                            }, edit = {
                                productViewModel.setProduct(product)
                                navigate()
                            })
                        }
                    }
                }
            }
        }
    }
}