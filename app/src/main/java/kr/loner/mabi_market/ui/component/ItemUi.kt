package kr.loner.mabi_market.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.R
import kr.loner.mabi_market.data.Item
import kr.loner.mabi_market.feature.main.MainViewModel
import kr.loner.mabi_market.ui.theme.BLUE05
import kr.loner.mabi_market.ui.theme.GRAY02
import kr.loner.mabi_market.util.koreanFormattedPrice
import kr.loner.mabi_market.util.readableTime

@Composable
fun ListItem(
    item: Item,
    modifier: Modifier = Modifier,
    onLikeClick: (Item) -> Unit,
    onShowDetail: (Item) -> Unit,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                onClick = { onShowDetail(item) },
                indication = rememberRipple(
                    bounded = true,
                    color = BLUE05
                ),
                interactionSource = remember { MutableInteractionSource() }
            )) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    BorderStroke(1.dp, Color.White),
                    RoundedCornerShape(3.dp)
                )
                .padding(4.dp)

        ) {
            //추후 마비노기 api 에서 주는 이미지 사용 예정
            Image(
                painter = painterResource(id = item.sampleImage),
                contentDescription = "샘플 이미지",
                modifier = Modifier.size(42.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, top = 4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = GRAY02
                )

                Text(
                    text = item.registerTimeStamp.readableTime,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp),
                    color = GRAY02
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "개당: ${item.price.koreanFormattedPrice}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GRAY02
                )

                Text(
                    text = "전체: ${item.allPrice.koreanFormattedPrice}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GRAY02
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (item.myLike) {
                            R.drawable.ic_like_on
                        } else {
                            R.drawable.ic_like_off
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .clickable(
                            onClick = { onLikeClick(item) },
                            indication = rememberRipple(
                                bounded = true,
                                color = BLUE05
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                )
            }

        }
    }
}

@Composable
fun ItemList(
    searchList: List<Item>,
    onMyLikeChange:(Item) -> Unit
) {
    val context = LocalContext.current
    LazyColumn {
        itemsIndexed(searchList) { i, item ->
            ListItem(item, onLikeClick = { likeItem ->
                onMyLikeChange(likeItem)
            },
                onShowDetail = { clikedItem ->
                    Toast.makeText(context, "기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
