package app.kotlin.crudproductos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.kotlin.crudproductos.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

enum class  SplashRoute{
    AUTH, HOME
}
@Composable
fun SplashScreen (navigate:(SplashRoute)->Unit){
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.designer)
    )
   val auth = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        delay(5000)
        if(auth != null){
            //navegamos directamente a la pantall home
            navigate(SplashRoute.AUTH)
        }else{
            //navegamos hacia la pantalla de login
            navigate(SplashRoute.AUTH)
        }

    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever
        )
        CircularProgressIndicator()
    }
}