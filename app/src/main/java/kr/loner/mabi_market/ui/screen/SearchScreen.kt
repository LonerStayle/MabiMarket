package kr.loner.mabi_market.ui.screen

import android.widget.Toast
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.R
import kr.loner.mabi_market.feature.main.MainViewModel
import kr.loner.mabi_market.ui.component.SearchBar
import kr.loner.mabi_market.ui.theme.BLUE03
import kr.loner.mabi_market.ui.theme.BLUE05

@Composable
fun SearchScreen(mainViewModel: MainViewModel, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        val uiState = mainViewModel.uiState.collectAsState().value
        val searchRecords = mainViewModel.searchRecords.collectAsState().value
        val context = LocalContext.current
        val focusRequester = remember { FocusRequester() }

        // 키보드를 자동으로 올리기 위한 Focus 요청
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        SearchBar(
            text = uiState.searchText,
            focusRequester = focusRequester,
            onBackClick = { onBack() },
            onTextChange = { mainViewModel.setSearchText(it) }
        ) {
            mainViewModel.search {
                onBack()
                Toast.makeText(context, "검색 기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 40.dp, vertical = 20.dp
                )
        ) {
            items(searchRecords) { searchRecord ->
                Text(
                    text = searchRecord,
                    style = MaterialTheme.typography.bodyMedium,
                    color = BLUE05
                )
            }
        }
    }
}

