package app.kotlin.crudproductos.data.model

data class Product (
    val id:String="",
    val name:String="",
    val price:String="",
    val stock:String="",
    val images: List<String> = emptyList()
)