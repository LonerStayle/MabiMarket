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
import kr.loner.mabi_market.ui.theme.BLUE03
import kr.loner.mabi_market.ui.theme.BLUE05


@Composable
fun SearchFilterScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    Toast.makeText(context, "필터 목록 준비중 입니다.", Toast.LENGTH_SHORT).show()
    Column(modifier = Modifier.fillMaxSize()) {
        val uiState = viewModel.uiState.collectAsState().value
        val filterText = uiState.searchFilterText

        Row(modifier = Modifier.padding(start = 20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "back",
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        onBack()
                    })
        }
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Spacer(modifier = Modifier.height(12.dp))
        SearchFilterBar(
            text = filterText,
            focusRequester = focusRequester,
            onTextChange = {
                viewModel.setFilterText(it)
            }, onSearchClick = {
                Toast.makeText(context, "기능 준비중 입니다.", Toast.LENGTH_SHORT).show()
//                onBack()
            }
        )
    }
}

@Composable
private fun SearchFilterBar(
    text: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current // 키보드 컨트롤러 생성
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Color(0x4DFDFDFD), RoundedCornerShape(40.dp))
                .border(1.dp, Color.White, RoundedCornerShape(40.dp))
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Menu",
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier
                    .background(BLUE05)
                    .width(1.dp)
                    .height(16.dp)

            )

            Spacer(modifier = Modifier.width(20.dp))

            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = BLUE05),
                maxLines = 1, // 한 줄만 허용
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onSearchClick()
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "조건 찾기",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = BLUE03
                                ),
                                textAlign = TextAlign.Start
                            )
                        }
                        innerTextField() // Actual TextField displayed here
                    }
                },
                cursorBrush = SolidColor(BLUE05)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_search_filter),
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





