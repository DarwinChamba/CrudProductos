package app.kotlin.crudproductos.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.kotlin.crudproductos.ui.components.ButtonLogin
import app.kotlin.crudproductos.ui.components.DialogResult
import app.kotlin.crudproductos.ui.components.TextFieldEmail
import app.kotlin.crudproductos.ui.components.TextFieldPassword
import app.kotlin.crudproductos.ui.components.TextFieldUi
import app.kotlin.crudproductos.ui.viewmodel.AuthViewModel
import app.kotlin.crudproductos.util.ProductState
import kotlinx.coroutines.flow.compose

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = viewModel(),
    navigate:()->Unit
) {

    val user by authViewModel.user.collectAsState()
    val validation by authViewModel.validation.collectAsState()
    val result by authViewModel.result.collectAsState()
    DialogResult(result,
        reset = {
            if(result is ProductState.Success){
                navigate()
            }
            authViewModel.reset()

        },
        aceptar = {
            navigate()
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 30.dp,
                horizontal = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Name
        TextFieldUi(
            user.name,
            "Name",
            KeyboardType.Text
        ) {
            authViewModel.setName(it)
        }

        //Email
        TextFieldEmail(
            user.email,
            "Email",
            validation.email,
            KeyboardType.Email
        ) {
            authViewModel.setEmail(it)
        }

        //password
        TextFieldPassword(
            user.password,
            "Password",
            validation.password,
            KeyboardType.Password
        ) {
            authViewModel.setPassword(it)
        }

        Spacer(modifier = Modifier.height(10.dp))
        ButtonLogin(authViewModel.enableButton()) {
            authViewModel.createUserEmailPassword()
        }
    }

}