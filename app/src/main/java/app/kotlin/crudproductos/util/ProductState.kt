package app.kotlin.crudproductos.util

sealed class ProductState<out T> {
    object  Idle: ProductState<Nothing>()
    object Loading: ProductState<Nothing>()
    data class  Error(val message:String): ProductState<Nothing>()
    data class Success<T>(val data:T): ProductState<T>()
}