package kr.loner.mabi_market.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null
) {
    AsyncImage(
        model = imageUrl,
        modifier = modifier,
        placeholder = placeholder,
        contentScale = contentScale,
        contentDescription = contentDescription
    )
}