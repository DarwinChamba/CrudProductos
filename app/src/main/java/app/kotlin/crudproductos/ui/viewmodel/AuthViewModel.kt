package app.kotlin.crudproductos.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kotlin.crudproductos.data.model.LoginValidation
import app.kotlin.crudproductos.data.model.PasswordValidation
import app.kotlin.crudproductos.data.model.User
import app.kotlin.crudproductos.data.repository.LoginRepository
import app.kotlin.crudproductos.util.ProductState
import com.google.android.play.integrity.internal.l
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AuthViewModel : ViewModel() {
    private val repository = LoginRepository()
    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> get() = _user

    private val _validation =
        MutableStateFlow(LoginValidation())
    val validation: StateFlow<LoginValidation> get() = _validation

    private val _result =
        MutableStateFlow<ProductState<String>>(ProductState.Idle)
    val result: StateFlow<ProductState<String>> get() = _result


    fun setName(name: String) {
        _user.update {
            it.copy(name = name)
        }
    }

    fun setEmail(email: String) {
        _user.update {
            it.copy(email = email)
        }

        _validation.update {
            it.copy(email = repository.validateEmail(email))
        }
    }

    fun setPassword(password: String) {
        _user.update {
            it.copy(password = password)
        }
        _validation.update {
            it.copy(password = repository.validatePassword(password))
        }

    }
    fun createUserEmailPassword(){
        viewModelScope.launch {
            repository.createUserEmailPassword(_user.value){
                _result.value = it
            }
        }
    }

    fun reset(){
        _result.value = ProductState.Idle
        _user.value = User()
    }

    fun enableButton(): Boolean {
        val name = _user.value.name
        val validation = _validation.value

        return name.isNotEmpty() &&
                validation.email == null &&
                validation.password.hasNumber &&
                validation.password.hasUpperCase &&
                validation.password.minLength
    }

    fun enableButtonSignIn(): Boolean {
        val validation = _validation.value

        return validation.email == null &&
                validation.password.hasNumber &&
                validation.password.hasUpperCase &&
                validation.password.minLength
    }

    fun signIntEmailPassword(){
        val user =_user.value
        viewModelScope.launch {
            repository.signInEmailPassword(user.email,user.password){
                _result.value = it
            }
        }
    }

    fun signInGoogle(idToken: String){
        viewModelScope.launch {
            repository.signInGoogle(idToken){
                _result.value = it
            }
        }
    }

}