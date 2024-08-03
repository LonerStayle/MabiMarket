package kr.loner.mabi_market.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kr.loner.mabi_market.R

@Composable
fun SplashScreen(splashEnd:()->Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
    ) {
        val visible by remember { mutableStateOf(true) }
        AnimatedVisibility(visible, enter = fadeIn(
            initialAlpha = 0.0f
        ), exit = fadeOut()) {
            Image(
                painter = painterResource(id = R.drawable.img_splash),
                contentDescription = "Splash Image",
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp)
            )
            LaunchedEffect(Unit) {
                delay(2000)
                splashEnd()
            }
        }
    }
}