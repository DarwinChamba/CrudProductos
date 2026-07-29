package app.kotlin.crudproductos.data.model

data class  User(
    val id:String="",
    val name:String="",
    val email: String="",
    val image:String="",
    val password:String=""
)

data class  LoginValidation(
    val email: String?= null,
    val password: PasswordValidation=PasswordValidation()
)
data class PasswordValidation(
    val hasNumber: Boolean=false,
    val hasUpperCase: Boolean=false,
    val minLength: Boolean=false
)
