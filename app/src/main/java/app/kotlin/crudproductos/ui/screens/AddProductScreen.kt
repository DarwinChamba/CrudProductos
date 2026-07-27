package app.kotlin.crudproductos.ui.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.kotlin.crudproductos.ui.components.ButtonImages
import app.kotlin.crudproductos.ui.components.CardImages
import app.kotlin.crudproductos.ui.components.DialogResult
import app.kotlin.crudproductos.ui.components.IndicadorImage
import app.kotlin.crudproductos.ui.components.SaveButton
import app.kotlin.crudproductos.ui.components.TextFieldUi
import app.kotlin.crudproductos.ui.components.TextUi
import app.kotlin.crudproductos.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.compose


@Composable
fun AddProductScreen(
    productViewModel: ProductViewModel,
    navigate: () -> Unit
) {
    val product by productViewModel.product.collectAsState()
    val context = LocalContext.current
    val isEdit by productViewModel.isEdit.collectAsState()
    //estado para las imágenes
    val listImage by productViewModel.imagesList.collectAsState()
    val pager = rememberPagerState(pageCount = { listImage.size })

    val result by productViewModel.result.collectAsState()
    DialogResult(
        result, reset = { productViewModel.reset() },
        aceptar = {
            if (isEdit) {
                navigate()
                productViewModel.reset()
            } else {
                productViewModel.reset()
            }
        })
    BackHandler {
        productViewModel.reset()
        navigate()
    }


    val launher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) {
            productViewModel.setList(it, context)
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextUi("Agregar Productos")
        //nombre
        TextFieldUi(
            product.name,
            "Nombre del producto",
            KeyboardType.Text
        ) {
            productViewModel.setNameProduct(it)
        }

        //precio
        TextFieldUi(
            product.price,
            "Precio",
            KeyboardType.Decimal
        ) {
            productViewModel.setPrice(it)
        }
        //stock
        TextFieldUi(
            product.stock,
            "Stock",
            KeyboardType.Number
        ) {
            productViewModel.setStock(it)
        }
        //seleccionar imagenes
        ButtonImages {
            launher.launch("image/*")
        }
        //mostrar imágenes en el HorizontalPager
        HorizontalPager(
            pager
        ) { page ->
            CardImages(listImage[page]) {
                productViewModel.deleteImageLocalAnRemote(page)
            }
        }
        //indicador de imagenes
        IndicadorImage(listImage.size, pager.currentPage)
        //boton para guardar productos

        SaveButton {
            if (isEdit) {
                productViewModel.updateProduct()
            } else {
                productViewModel.saveProduct()
            }
        }
    }
}