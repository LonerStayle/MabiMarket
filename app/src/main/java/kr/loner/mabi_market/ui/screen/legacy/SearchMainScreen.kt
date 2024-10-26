package kr.loner.mabi_market.ui.screen.legacy

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.R
import kr.loner.mabi_market.data.legacy.Item
import kr.loner.mabi_market.data.model.ServerType
import kr.loner.mabi_market.feature.legacy.main.MainViewModel
import kr.loner.mabi_market.feature.legacy.my_like.MyLikeActivity
import kr.loner.mabi_market.ui.component.legacy.ItemList
import kr.loner.mabi_market.ui.component.SearchFilterBox
import kr.loner.mabi_market.ui.component.SearchFilterToggle
import kr.loner.mabi_market.ui.theme.BLUE03
import kr.loner.mabi_market.ui.theme.BLUE05

@Composable
fun SearchMainScreen(mainViewModel: MainViewModel, openFilter: () -> Unit, openSearch: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {


        val uiState = mainViewModel.uiState.collectAsState().value
        val context = LocalContext.current
        val searchList = mainViewModel.searchItemList.collectAsState().value
        SearchDisableBar(
            text = uiState.searchCompletedTextOrHint,
            onMenuClick = {
                openFilter()
            },

            onSearchClick = {
                openSearch()
            })

        Divider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(top = 16.dp))

        Box(modifier = Modifier.weight(1f)) {
            SearchResultList(searchList, mainViewModel)
            SearchFilter(uiState, mainViewModel)

            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_like_big_off),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            val intent = Intent(context, MyLikeActivity::class.java)
                            context.startActivity(intent)
                        }
                )
            }
        }
    }
}


@Composable
private fun SearchFilter(
    uiState: MainViewModel.MainUiState,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(37.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchFilterToggle(uiState.isVisibleToggleServer, uiState.selectServer.desc) {
                mainViewModel.isVisibleToggleServer(!uiState.isVisibleToggleServer)
            }
            SearchFilterToggle(uiState.isVisibleToggleTime, uiState.selectTime?.desc ?: "시간순") {
                mainViewModel.isVisibleToggleTime(!uiState.isVisibleToggleTime)
            }
            SearchFilterToggle(
                uiState.isVisibleTogglePriceOrder,
                uiState.selectPriceOrder?.desc ?: "가격순"
            ) {
                mainViewModel.isVisibleTogglePriceOrder(!uiState.isVisibleTogglePriceOrder)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (uiState.isVisibleToggleServer) {
                SearchFilterBox(
                    uiState.selectServer.desc,
                    listOf(
                        ServerType.RYUTE.desc,
                        ServerType.MANDOLIN.desc,
                        ServerType.HARF.desc,
                        ServerType.WOLF.desc
                    )
                ) {
                    mainViewModel.setSelectServer(it) {
                        Toast.makeText(context, "기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
                    }
                    mainViewModel.isVisibleToggleServer(false)
                }
            } else {
                //3개의 뷰 간격 유지를 위해 필요함
                Spacer(modifier = Modifier.width(60.dp))
            }

            if (uiState.isVisibleToggleTime) {
                SearchFilterBox(
                    uiState.selectTime?.desc ?: "",
                    listOf(
                        MainViewModel.SelectTimeType.DESC.desc,
                        MainViewModel.SelectTimeType.ASC.desc
                    )
                ) {
                    mainViewModel.setSelectTime(it) {
                        Toast.makeText(context, "기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
                    }
                    mainViewModel.isVisibleToggleTime(false)
                }
            } else {
                //3개의 뷰 간격 유지를 위해 필요함
                Spacer(modifier = Modifier.width(60.dp))
            }

            if (uiState.isVisibleTogglePriceOrder) {
                SearchFilterBox(
                    uiState.selectPriceOrder?.desc ?: "", listOf(
                        MainViewModel.SelectPriceType.DESC.desc,
                        MainViewModel.SelectPriceType.ASC.desc
                    )
                ) {
                    mainViewModel.setSelectPriceOrder(it) {
                        Toast.makeText(context, "기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
                    }
                    mainViewModel.isVisibleTogglePriceOrder(false)
                }
            } else {
                //3개의 뷰 간격 유지를 위해 필요함
                Spacer(modifier = Modifier.width(60.dp))
            }
        }
    }
}


@Composable
private fun SearchResultList(
    searchList: List<Item>,
    mainViewModel: MainViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            thickness = 3.dp,
            color = Color.White,
            modifier = Modifier.padding(top = 37.dp)
        )
        ItemList(searchList) { likeItem ->
            mainViewModel.setIsMyLike(likeItem)
        }
    }
}


@Composable
private fun SearchDisableBar(
    text: String,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Search Menu",
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .clickable(
                    onClick = onMenuClick,
                    indication = rememberRipple(
                        bounded = true,
                        color = BLUE05
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        )
        Spacer(modifier = Modifier.width(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Color(0x4DFDFDFD), RoundedCornerShape(40.dp))
                .border(1.dp, Color.White, RoundedCornerShape(40.dp))
                .padding(horizontal = 20.dp)
                .clickable { onSearchClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = BLUE03
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(
                        onClick = onSearchClick,
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