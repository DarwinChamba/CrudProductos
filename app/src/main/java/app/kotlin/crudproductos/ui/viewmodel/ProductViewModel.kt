package app.kotlin.crudproductos.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kotlin.crudproductos.data.model.Product
import app.kotlin.crudproductos.data.repository.ProductRepository
import app.kotlin.crudproductos.util.ImageState
import app.kotlin.crudproductos.util.ProductState
import app.kotlin.crudproductos.util.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _product = MutableStateFlow(Product())
    val product: StateFlow<Product> get() = _product

    private val _imagesList =
        MutableStateFlow<List<ImageState>>(listOf())
    val imagesList: StateFlow<List<ImageState>> get() = _imagesList


    private val _result =
        MutableStateFlow<ProductState<String>>(ProductState.Idle)
    val result: StateFlow<ProductState<String>> get() = _result

    private val _listProduct =
        MutableStateFlow<ProductState<List<Product>>>(ProductState.Idle)
    val listProduct: StateFlow<ProductState<List<Product>>> get() = _listProduct

    val isEdit =MutableStateFlow(false)

    init {
        getAllProduct()
    }

    fun setProduct(product: Product){
        _product.value =product
        isEdit.value=true
        val list =mutableListOf<ImageState>()
        product.images.forEach {

            list.add(  ImageState.ImageRemote(it))
        }
        //establecer una lista de tipo ImageState
        _imagesList.value = list
    }
    fun setList(listUri: List<Uri>, context: Context) {

        //creamos una lista de tipo ImageState
        /*
        ImageState puede ser de dos tipos ImageLocal e ImageRemote
         */
        val list = mutableListOf<ImageState>()
        //recuperamos el estado actual de las imagenes
        val currentImage = _imagesList.value
        //rrecorrer la lista de imagenes

        currentImage.forEach {
                list.add(it)
        }
        listUri.forEach {
            val byteArray = uriToByteArray(it, context)
            byteArray?.let { byteArray ->
                list.add(ImageState.ImageLocal(byteArray))
            }
        }

        _imagesList.value = list
    }

    fun deleteImageLocalAnRemote(position: Int) {
        val currentImage = _imagesList.value.toMutableList()
        val image =currentImage[position]

        if(image is ImageState.ImageRemote){
            viewModelScope.launch {
                repository.deleteImageSupabase(image.imageRemote
                    .substringAfterLast("/"),_product.value.id,position)
            }
        }
        currentImage.removeAt(position)
        _imagesList.value = currentImage
    }

    private fun uriToByteArray(uri: Uri, context: Context): ByteArray? {
        return context.contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        }
    }

    fun setNameProduct(name: String) {
        _product.update { product ->
            product.copy(name = name)
        }
    }

    fun setPrice(price: String) {
        _product.update { product ->
            product.copy(price = price)
        }
    }

    fun setStock(stock: String) {
        _product.update { product ->
            product.copy(stock = stock)
        }
    }


    fun saveProduct() {
        viewModelScope.launch {
            repository.saveProduct(
                _product.value,
                _imagesList.value
            ) {
                _result.value = it
            }
        }
    }

    fun updateProduct() {
        viewModelScope.launch {
            repository.updateProduct(
                _product.value,
                _imagesList.value
            ) {
                _result.value = it
            }
        }
    }


    fun getAllProduct(){
        viewModelScope.launch {
            repository.getAllProduct {
                _listProduct.value =it
            }
        }
    }

    fun deleteProduct(id: String){
        repository.deleteProduct(id)
    }



    fun reset() {
        _product.value = Product()
        _result.value = ProductState.Idle
        _imagesList.value = listOf()
        isEdit.value=false
    }



}