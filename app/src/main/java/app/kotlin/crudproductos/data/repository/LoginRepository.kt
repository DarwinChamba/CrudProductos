package app.kotlin.crudproductos.data.repository

import android.R.attr.password
import android.content.Context
import android.util.Patterns
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import app.kotlin.crudproductos.data.model.PasswordValidation
import app.kotlin.crudproductos.data.model.User
import app.kotlin.crudproductos.util.ProductState
import coil.util.CoilUtils.result
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.util.UUID

class LoginRepository {

    val reference = FirebaseDatabase.getInstance()
        .getReference("user")
    val auth = FirebaseAuth.getInstance()

    suspend fun createUserEmailPassword(
        user: User,
        result: (ProductState<String>) -> Unit
    ) {
        result(ProductState.Loading)
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: UUID.randomUUID().toString()
                reference.child(uid).setValue(user.copy(id = uid, password = ""))
                    .addOnSuccessListener {
                        result(ProductState.Success("Usuario autenticado"))

                    }.addOnFailureListener {

                        result(ProductState.Error(it.message.toString()))
                    }
            }.addOnFailureListener {

                result(ProductState.Error(it.message.toString()))
            }

    }

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email requerido"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "formato incorrecto"
            else -> null
        }
    }

    fun validatePassword(password: String): PasswordValidation {
        return PasswordValidation(
            hasNumber = password.any { it.isDigit() },
            hasUpperCase = password.any { it.isUpperCase() },
            minLength = password.length > 7
        )
    }

    suspend fun signInEmailPassword(
        email: String,
        password: String,
        result: (ProductState<String>) -> Unit
    ) {


        result(ProductState.Loading)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val name = it.user?.displayName ?: ""
                result(
                    ProductState.Success(
                        "Hola bienvenido $name"
                    )
                )
            }.addOnFailureListener {
                result(ProductState.Error(it.message.toString()))
            }
    }


    suspend fun signInGoogle(
        idToken: String, result: (ProductState<String>) -> Unit
    ) {
        try {
            result(ProductState.Loading)
            val authCredential =
                GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(authCredential).await()
            val user = authResult.user
            user?.let {
                val id = user.uid
                val user = User(
                    id = id,
                    name = it.displayName!!,
                    email = it.email!!,
                    image = it.photoUrl.toString()
                )

                reference.child(id).setValue(user).await()
                result(ProductState.Success("Hola  bienvenido  ${it.displayName}"))

            }


        } catch (e: Exception) {
            result(ProductState.Error(e.message.toString()))
        }
    }
}