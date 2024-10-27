package kr.loner.mabi_market.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.R
import kr.loner.mabi_market.data.model.ServerType
import kr.loner.mabi_market.feature.burn_bugle_keyword.BurnBugleKeywordActivity
import kr.loner.mabi_market.feature.main.NewMainViewModel
import kr.loner.mabi_market.ui.component.SelectServerFilter
import kr.loner.mabi_market.ui.theme.GRAY666

@Composable
fun MainScreen(viewModel: NewMainViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            viewModel.showApiTypeList(false)
        }) {
        val uiState = viewModel.uiState.collectAsState().value
        val curServerType = viewModel.curServerType.collectAsState().value ?: ServerType.RYUTE


        Box(modifier = Modifier.weight(1f)) {
            TopFilterUi(uiState, selectType = { type ->
                viewModel.selectApiType(type)
            }, showList = { isShow ->
                viewModel.showApiTypeList(isShow)
            })

            ContentUi(curServerType) {
                viewModel.selectCurServer(it)
            }
        }
    }
}

@Composable
private fun ContentUi(curServerType: ServerType, onSelectServer: (ServerType) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(top = 61.dp)) {
        SelectServerFilter(curServerType) {
            onSelectServer(it)
        }
    }
}


@Composable
private fun BornBugleUi(uiState: NewMainViewModel.NewMainUiState) {

}

@Composable
private fun AuctionUi(uiState: NewMainViewModel.NewMainUiState) {

}

@Composable
private fun TopFilterUi(
    uiState: NewMainViewModel.NewMainUiState,
    selectType: (NewMainViewModel.ApiType) -> Unit,
    showList: (Boolean) -> Unit,
) {

    Row(modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 10.dp)) {
        val context = LocalContext.current
        Column {
            ApiSelectItem(
                type = uiState.curApiType,
                alreadySelect = true,
            ) {
                showList(!uiState.showApiTypeList)
            }


            val entries = NewMainViewModel.ApiType.entries.toMutableList()
            val selectIndex = entries.indexOfFirst { it == uiState.curApiType }
            if (selectIndex != -1) {
                entries.removeAt(selectIndex)
            }
            if (uiState.showApiTypeList) {
                LazyColumn {
                    items(entries) { type ->
                        ApiSelectItem(
                            type = type,
                            alreadySelect = false
                        ) { newSelectType ->
                            selectType(newSelectType)
                            if (newSelectType != uiState.curApiType) {
                                showList(false)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_search_28),
            modifier = Modifier
                .size(28.dp)
                .clickable {

                },
            contentDescription = "Search"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_born),
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    context.startActivity(Intent(context, BurnBugleKeywordActivity::class.java))
                },
            contentDescription = "Born"
        )

    }

}

@Composable
private fun ApiSelectItem(
    type: NewMainViewModel.ApiType,
    alreadySelect: Boolean,
    selectType: (NewMainViewModel.ApiType) -> Unit
) {

    Row(
        modifier = Modifier
            .width(170.dp)
            .height(37.dp)
            .background(Color(0x4DFDFDFD), RoundedCornerShape(28.5.dp))
            .border(1.dp, Color.White, RoundedCornerShape(28.5.dp))
            .padding(horizontal = 12.dp)
            .clickable {
                selectType(type)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (alreadySelect) type.desc + " ▼" else type.desc,
            color = GRAY666,
            textDecoration = null,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }


}