package app.kotlin.crudproductos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.kotlin.crudproductos.data.model.GoogleAuthProvideManager
import app.kotlin.crudproductos.ui.components.ButtonGoogle
import app.kotlin.crudproductos.ui.components.ButtonLogin
import app.kotlin.crudproductos.ui.components.DialogResult
import app.kotlin.crudproductos.ui.components.NoCuenta
import app.kotlin.crudproductos.ui.components.TextFieldEmail
import app.kotlin.crudproductos.ui.components.TextFieldPassword
import app.kotlin.crudproductos.ui.viewmodel.AuthViewModel
import app.kotlin.crudproductos.util.Navigate
import app.kotlin.crudproductos.util.ProductState
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch


@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = viewModel(),
    navigate: (Navigate) -> Unit
) {

    val user by authViewModel.user.collectAsState()
    val validation by authViewModel.validation.collectAsState()
    val result by authViewModel.result.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    DialogResult(
        result,
        reset = {
            if (result is ProductState.Success) {
                navigate(Navigate.HOME)
            }
            authViewModel.reset()

        },
        aceptar = {
            navigate(Navigate.HOME)
            authViewModel.reset()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {//llave de inicio del Layout Column

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
        Spacer(modifier = Modifier.height(20.dp))

        //boton para continuar con email/password
        ButtonLogin(enabled = authViewModel.enableButtonSignIn()) {
            authViewModel.signIntEmailPassword()
        }
        //boton para continuar con google
        ButtonGoogle {
            scope.launch {
             val idToken=   GoogleAuthProvideManager.getIdToken(context)
                idToken?.let{
                    authViewModel.signInGoogle(it)
                }
            }
        }
        //si el usuario preciona en el texto navegamos hacia la pantalla para registrar un usuario
        NoCuenta(navigate = {
            navigate(Navigate.REGISTER)
        })

    }//llave de cierre del Layout Column


}