package app.kotlin.crudproductos.util

sealed class ImageState {
    data class ImageLocal(val byteArray: ByteArray): ImageState()
    data class ImageRemote(val imageRemote:String): ImageState()
}