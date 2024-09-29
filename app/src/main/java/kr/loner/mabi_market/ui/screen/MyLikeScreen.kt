package kr.loner.mabi_market.ui.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.R
import kr.loner.mabi_market.data.Item
import kr.loner.mabi_market.data.ItemServerType
import kr.loner.mabi_market.feature.main.MainViewModel
import kr.loner.mabi_market.feature.my_like.MyLikeActivity
import kr.loner.mabi_market.feature.my_like.MyLikeViewModel
import kr.loner.mabi_market.ui.component.ItemList
import kr.loner.mabi_market.ui.component.SearchBar
import kr.loner.mabi_market.ui.component.SearchFilterBox
import kr.loner.mabi_market.ui.component.SearchFilterToggle

@Composable
fun MyLikeScreen(viewModel: MyLikeViewModel, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        val uiState = viewModel.uiState.collectAsState().value
        val context = LocalContext.current
        val itemList = viewModel.itemLikeFilters.collectAsState().value
        SearchBar(
            text = uiState.searchText,
            onBackClick = { onBack() },
            onTextChange = { viewModel.setSearchText(it) },
            onSearchClick = { })

        Divider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(top = 16.dp))

        Box(modifier = Modifier.weight(1f)) {
            SearchResultList(itemList, viewModel)
            SearchFilter(uiState, viewModel)

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
    uiState: MyLikeViewModel.MyLikeUiState,
    myLikeViewModel: MyLikeViewModel
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
                myLikeViewModel.isVisibleToggleServer(!uiState.isVisibleToggleServer)
            }
            SearchFilterToggle(uiState.isVisibleToggleTime, uiState.selectTime?.desc ?: "시간순") {
                myLikeViewModel.isVisibleToggleTime(!uiState.isVisibleToggleTime)
            }
            SearchFilterToggle(
                uiState.isVisibleTogglePriceOrder,
                uiState.selectPriceOrder?.desc ?: "가격순"
            ) {
                myLikeViewModel.isVisibleTogglePriceOrder(!uiState.isVisibleTogglePriceOrder)
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
                        ItemServerType.RYUTE.desc,
                        ItemServerType.MANDOLIN.desc,
                        ItemServerType.HARF.desc,
                        ItemServerType.WOLF.desc
                    )
                ) {
                    myLikeViewModel.setSelectServer(it)
                    myLikeViewModel.isVisibleToggleServer(false)
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
                    myLikeViewModel.setSelectTime(it)
                    myLikeViewModel.isVisibleToggleTime(false)
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
                    myLikeViewModel.setSelectPriceOrder(it)
                    myLikeViewModel.isVisibleTogglePriceOrder(false)
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
    myLikeViewModel: MyLikeViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            thickness = 3.dp,
            color = Color.White,
            modifier = Modifier.padding(top = 37.dp)
        )
        ItemList(searchList){ likeItem ->
            myLikeViewModel.setIsMyLike(likeItem)
        }
    }
}


