package app.kotlin.crudproductos.data.repository

import android.R.attr.data
import androidx.compose.material3.SnackbarData
import app.kotlin.crudproductos.data.model.Product
import app.kotlin.crudproductos.util.ImageState
import app.kotlin.crudproductos.util.ProductState
import app.kotlin.crudproductos.util.SupabaseClient
import coil.util.CoilUtils.result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.jan.supabase.storage.storage
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.update
import java.util.UUID

class ProductRepository {

    val reference =
        FirebaseDatabase.getInstance().getReference("product")

    suspend fun saveProduct(
        product: Product,
        currentImage: List<ImageState>,
        result: (ProductState<String>) -> Unit
    ) {
        val uid = UUID.randomUUID().toString()
        result(ProductState.Loading)
        val list = getPublicUrl(currentImage)
        reference.child(uid).setValue(product.copy(id = uid, images = list))
            .addOnSuccessListener {
                result(ProductState.Success("Registro realizo con exito"))
            }.addOnFailureListener {
                result(ProductState.Error(it.message.toString()))
            }
    }


    suspend fun updateProduct(
        product: Product,
        currentImage: List<ImageState>,
        result: (ProductState<String>) -> Unit
    ) {

        result(ProductState.Loading)
        val list = getPublicUrl(currentImage)
        reference.child(product.id).setValue(product.copy( images = list))
            .addOnSuccessListener {
                result(ProductState.Success("Registro modificado con exito"))
            }.addOnFailureListener {
                result(ProductState.Error(it.message.toString()))
            }
    }

    private suspend fun getPublicUrl(currentImage: List<ImageState>): List<String> {

        val client = SupabaseClient.cliente
        val listPublicUrl = mutableListOf<String>()
        currentImage.forEach {
            when(it){
                is ImageState.ImageLocal -> {

                        val byteArray = it.byteArray
                        val image = "${System.currentTimeMillis()}.jpg"
                        client.storage.from("image")
                            .upload(
                                image,
                                byteArray
                            )

                        val publicUrl = client.storage.from("image")
                            .publicUrl(image.substringAfterLast("/"))

                        listPublicUrl.add(publicUrl)

                }
                is ImageState.ImageRemote -> {
                    listPublicUrl.add(it.imageRemote)
                }
            }



        }
        return listPublicUrl

    }

    suspend fun getAllProduct(result: (ProductState<List<Product>>) -> Unit) {
        result(ProductState.Loading)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val list = mutableListOf<Product>()
                    for (data in dataSnapshot.children) {
                        val product = data.getValue(Product::class.java)
                        product?.let {
                            list.add(it)
                        }
                    }//llave de cierre del bucle
                    result(ProductState.Success(list))
                } else {
                    result(ProductState.Success(listOf()))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result(ProductState.Error(error.message))
            }

        })

    }

    fun deleteProduct(id: String) {
        reference.child(id).removeValue()

    }

    suspend fun deleteImageSupabase(name: String,productId:String,position:Int) {
        SupabaseClient.cliente.storage.from("image")
            .delete(name)
        reference.child(productId).get().addOnSuccessListener {
            if(it.exists()){
                val product = it.getValue(Product::class.java)
                product?.let {
                    val images = it.images.toMutableList()
                    images.removeAt(position)
                    reference.child(productId).child("images").setValue(images)
                }
            }
        }
    }


}