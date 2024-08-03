package kr.loner.mabi_market.ui.component


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import kr.loner.mabi_market.ui.theme.BLUE05
import kr.loner.mabi_market.ui.theme.GRAY01


@Composable
fun ServerSelectText(isSelect:Boolean, text:String, modifier:Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = text,
            color = Color.White,
            textDecoration = null,
            style = MaterialTheme.typography.bodyMedium.copy(
                shadow = null,
                drawStyle = Stroke(
                    width = 5f,
                    join = StrokeJoin.Miter,
                ),

            )
        )

        Text(
            text = text,
            color = if(isSelect) GRAY01 else BLUE05,
            textDecoration = null,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}