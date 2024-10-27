package kr.loner.mabi_market.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.data.model.ServerType
import kr.loner.mabi_market.ui.theme.BLUE02
import kr.loner.mabi_market.ui.theme.BLUE05

@Composable
fun SelectServerFilter(curServer: ServerType, onSelectServer: (ServerType) -> Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(ServerType.entries.toList()){ type ->
            SelectServerFilterItem(curServer,type){
                onSelectServer(it)
            }
        }
    }
}

@Composable
fun SelectServerFilterItem(
    curServer: ServerType,
    serverType: ServerType,
    onSelectServer: (ServerType) -> Unit
) {

    val isSelected = curServer == serverType
    val color = if (isSelected) {
        BLUE05
    } else {
        Color.White
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .border(1.dp, color)
            .clickable {
                if(!isSelected) onSelectServer(serverType)
            }
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            SelectText(
                modifier = Modifier.fillMaxWidth(),
                //ui가 안맞아서 반대로! 처리
                isSelect = !isSelected,
                text = serverType.desc
            )
        }
        Box(modifier = Modifier
            .weight(1f)
            .height(2.dp)
            .background(color)) {
        }
    }
}