package app.kotlin.crudproductos.util

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val cliente = createSupabaseClient(
        supabaseUrl = "https://cmqtnhtbywmbnqgtnzvr.supabase.co",
        supabaseKey = "sb_publishable_ig1cXk_XpWpWj-stF_RXFQ_9NCQCLAc"
    ){
        install(Storage)
    }
}