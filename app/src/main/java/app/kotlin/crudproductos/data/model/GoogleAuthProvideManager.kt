package app.kotlin.crudproductos.data.model

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

object GoogleAuthProvideManager {

    suspend fun getIdToken(context: Context): String? {
        val crendentialManager =
            CredentialManager.create(context)

        val googleIdOption =
            GetGoogleIdOption.Builder()
                .setServerClientId("521534136496-stkhuhskonp3c22g2begn8sqhha0u6vu.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credential =
            crendentialManager.getCredential(context, request).credential

        return if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)
            googleIdTokenCredential.idToken
        } else {
            null
        }

    }
}