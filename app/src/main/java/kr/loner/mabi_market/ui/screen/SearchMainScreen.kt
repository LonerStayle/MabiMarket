package kr.loner.mabi_market.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.feature.MainViewModel
import kr.loner.mabi_market.ui.component.SearchBar
import kr.loner.mabi_market.ui.component.SearchFilterBox
import kr.loner.mabi_market.ui.component.SearchFilterToggle

@Composable
fun SearchMainScreen(mainViewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        val uiState = mainViewModel.uiState.collectAsState().value
        val context = LocalContext.current
        SearchBar(
            text = uiState.searchText,
            onTextChange = { newText ->
                mainViewModel.setSearchText(newText)
            },
            onSearchClick = {
                mainViewModel.search {
                    Toast.makeText(context, "기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
                }
            })

        Divider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(top = 16.dp))

        Box(modifier = Modifier.weight(1f)) {
            SearchFilter(uiState, mainViewModel)
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider(thickness = 3.dp, color = Color.White, modifier = Modifier.padding(top = 37.dp))
                LazyColumn {

                }
            }
        }




    }
}

@Composable
private fun SearchFilter(
    uiState: MainViewModel.MainUiState,
    mainViewModel: MainViewModel
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(37.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchFilterToggle(uiState.isVisibleToggleServer, uiState.selectServer ?: "류트") {
                mainViewModel.isVisibleToggleServer(!uiState.isVisibleToggleServer)
            }
            SearchFilterToggle(uiState.isVisibleToggleTime, uiState.selectTime ?: "시간순") {
                mainViewModel.isVisibleToggleTime(!uiState.isVisibleToggleTime)
            }
            SearchFilterToggle(
                uiState.isVisibleTogglePriceOrder,
                uiState.selectPriceOrder ?: "가격순"
            ) {
                mainViewModel.isVisibleTogglePriceOrder(!uiState.isVisibleTogglePriceOrder)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (uiState.isVisibleToggleServer) {
                SearchFilterBox(uiState.selectServer ?: "", listOf("류트", "만돌린", "하프", "울프")) {
                    mainViewModel.setSelectServer(it)
                    mainViewModel.isVisibleToggleServer(false)
                }
            } else {
                //3개의 뷰 간격 유지를 위해 필요함
                Spacer(modifier = Modifier.width(60.dp))
            }

            if (uiState.isVisibleToggleTime) {
                SearchFilterBox(uiState.selectTime ?: "", listOf("최신 순", "오래된 순")) {
                    mainViewModel.setSelectTime(it)
                    mainViewModel.isVisibleToggleTime(false)
                }
            } else {
                //3개의 뷰 간격 유지를 위해 필요함
                Spacer(modifier = Modifier.width(60.dp))
            }

            if (uiState.isVisibleTogglePriceOrder) {
                SearchFilterBox(uiState.selectPriceOrder ?: "", listOf("가격↓", "가격↑")) {
                    mainViewModel.setSelectPriceOrder(it)
                    mainViewModel.isVisibleTogglePriceOrder(false)
                }
            } else {
                //3개의 뷰 간격 유지를 위해 필요함
                Spacer(modifier = Modifier.width(60.dp))
            }
        }
    }
}