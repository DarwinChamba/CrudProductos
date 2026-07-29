package app.kotlin.crudproductos.ui.components


import android.R.attr.onClick
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.kotlin.crudproductos.data.model.PasswordValidation
import app.kotlin.crudproductos.data.model.Product
import app.kotlin.crudproductos.util.ImageState
import app.kotlin.crudproductos.util.ProductState
import coil.compose.AsyncImage
import java.nio.file.Files.delete

@Composable
fun ButtonLogin(
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4C9EEB),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF4C9EEB).copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        contentPadding = PaddingValues(vertical = 14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ButtonGoogle(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        )
    ) {

        Text("Continuar con Google")
    }
}

@Composable
fun NoCuenta(navigate: () -> Unit) {
    Text(
        "¿No tienes cuenta ?.Registrate",
        modifier = Modifier.clickable {
            navigate()
        })
}


@Composable
fun DeleteDialog(
    showDialog: Boolean,
    aceptar: () -> Unit,
    cancelar: () -> Unit
) {
    if (showDialog) {


        Dialog(onDismissRequest = cancelar) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icono de advertencia
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE05C5C).copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = null,
                            tint = Color(0xFFE05C5C),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Eliminar elemento",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E1E1E)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "¿Estás seguro que deseas eliminarlo? Esta acción no se puede deshacer.",
                        fontSize = 14.sp,
                        color = Color(0xFF1E1E1E).copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(24.dp))

                    // Acciones lado a lado: Cancelar (secundario) | Eliminar (destructivo)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = cancelar,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFF1E1E1E).copy(alpha = 0.15f)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF1E1E1E)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Cancelar", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                        }

                        Button(
                            onClick = aceptar,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE05C5C)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Eliminar", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardProduct(
    product: Product,
    delete: () -> Unit,
    edit: () -> Unit
) {
    val pager = rememberPagerState(pageCount = { product.images.size })

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // ---- Header: nombre + precio ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E1E1E),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "$${product.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4C9EEB)
                )
            }

            // ---- Imagen ----
            HorizontalPager(
                state = pager,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) { page ->
                AsyncImage(
                    model = product.images[page],
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF5F5F5))
                )
            }

            Spacer(Modifier.height(10.dp))

            // ---- Indicador de imágenes (visible, debajo del pager) ----
            if (product.images.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(product.images.size) { index ->
                        val selected = index == pager.currentPage
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .height(6.dp)
                                .width(if (selected) 20.dp else 6.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (selected) Color(0xFF4C9EEB)
                                    else Color(0xFF1E1E1E).copy(alpha = 0.15f)
                                )
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
            }

            // Contador tipo "2/5" al lado del indicador
            if (product.images.size > 1) {
                Text(
                    text = "${pager.currentPage + 1}/${product.images.size}",
                    fontSize = 11.sp,
                    color = Color(0xFF1E1E1E).copy(alpha = 0.5f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFF1E1E1E).copy(alpha = 0.06f))

            // ---- Footer: stock + acciones ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StockBadge(stock = product.stock)

                Row {
                    TextButton(onClick = edit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color(0xFF4C9EEB),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Editar", color = Color(0xFF4C9EEB), fontSize = 13.sp)
                    }
                    TextButton(onClick = delete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFE05C5C),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Eliminar", color = Color(0xFFE05C5C), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun StockBadge(stock: String) {
    val inStock = stock.toIntOrNull()?.let { it > 0 } ?: true
    val bg =
        if (inStock) Color(0xFF4C9EEB).copy(alpha = 0.1f) else Color(0xFFE05C5C).copy(alpha = 0.1f)
    val fg = if (inStock) Color(0xFF4C9EEB) else Color(0xFFE05C5C)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Stock: $stock",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = fg
        )
    }
}


@Composable
fun DialogResult(
    productState: ProductState<String>,
    reset: () -> Unit,
    aceptar: () -> Unit
) {
    if (productState is ProductState.Idle) return

    Dialog(
        onDismissRequest = {
            if (productState !is ProductState.Loading) reset()
        }
    ) {

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFF5F5F5),
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {

            AnimatedContent(
                targetState = productState,
                label = "dialog_state",
                transitionSpec = {
                    fadeIn(tween(250)) togetherWith fadeOut(tween(150))
                }
            ) { state ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (state) {
                        is ProductState.Error -> {
                            StatusIcon(
                                icon = Icons.Default.Cancel,
                                background = Color(0xFFE05C5C)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "Ocurrió un error",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E1E1E)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                fontSize = 14.sp,
                                color = Color(0xFF1E1E1E).copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(24.dp))
                            DialogButton(
                                text = "Aceptar",
                                containerColor = Color(0xFFE05C5C),
                                onClick = reset
                            )
                        }

                        ProductState.Idle -> {}

                        ProductState.Loading -> {
                            CircularProgressIndicator(
                                color = Color(0xFF4C9EEB),
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(Modifier.height(20.dp))
                            Text(
                                text = "Cargando",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E1E1E)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Espere por favor...",
                                fontSize = 14.sp,
                                color = Color(0xFF1E1E1E).copy(alpha = 0.6f)
                            )
                        }

                        is ProductState.Success -> {
                            StatusIcon(
                                icon = Icons.Default.Check,
                                background = Color(0xFF4C9EEB)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "Resultado exitoso",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E1E1E)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = state.data,
                                fontSize = 14.sp,
                                color = Color(0xFF1E1E1E).copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(24.dp))
                            DialogButton(
                                text = "Aceptar",
                                containerColor = Color(0xFF4C9EEB),
                                onClick = aceptar
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusIcon(icon: ImageVector, background: Color) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(background.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = background,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun DialogButton(text: String, containerColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}


@Composable
fun SaveButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Text(text)
    }
}


@Composable
fun IndicadorImage(cantidad: Int, currentPage: Int) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(cantidad) { index ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(
                        if (currentPage == index) {
                            Color.Blue
                        } else {
                            Color.Gray
                        }
                    )
            )
        }

    }

}

@Composable
fun CardImages(imageState: ImageState, delete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        IconButton(onClick = delete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "",
                tint = Color.Red
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            when (val data = imageState) {
                is ImageState.ImageLocal -> {
                    AsyncImage(
                        model = data.byteArray,
                        contentDescription = "",
                        modifier = Modifier.size(300.dp)
                    )
                }

                is ImageState.ImageRemote -> {
                    AsyncImage(
                        model = data.imageRemote,
                        contentDescription = "",
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ButtonImages(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        )
    ) {
        Text("Seleccionar imagen")
    }
}

@Composable
fun TextUi(text: String) {
    Text(
        text, fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextFieldPassword(
    value: String,
    label: String,
    passwordValidation: PasswordValidation,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit

) {
    var show by rememberSaveable { mutableStateOf(false) }
    var isFcous by rememberSaveable { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFcous = it.isFocused
                },
            visualTransformation = if (show) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            label = {
                Text(label)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = {
                    show = !show
                }) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = ""
                    )
                }
            }
        )

        if (isFcous) { //llave de apertura


            if (!passwordValidation.minLength) {
                Text("Contraseña mayor a 7 caracteres", color = Color.Red)
            }


            if (!passwordValidation.hasUpperCase) {
                Text("Letra en mayuscula", color = Color.Red)
            }


            if (!passwordValidation.hasNumber) {
                Text("Al menos un número", color = Color.Red)
            }
        }//llave de cierre
    }
}


@Composable
fun TextFieldUi(
    value: String,
    label: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit

) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun TextFieldEmail(
    value: String,
    label: String,
    error: String?,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit

) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(label)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            isError = error != null
        )
        error?.let {
            Text(it, color = Color.Red)
        }
    }
}


@Composable
fun ButtonNavigate(navigate: () -> Unit) {
    FloatingActionButton(onClick = navigate) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = ""
        )
    }
}

