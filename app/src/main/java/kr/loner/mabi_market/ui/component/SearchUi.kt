package kr.loner.mabi_market.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.loner.mabi_market.R
import kr.loner.mabi_market.ui.theme.BLUE03
import kr.loner.mabi_market.ui.theme.BLUE05

@Composable
fun SearchBar(
    text: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester.Default,
    onBackClick: () -> Unit,
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
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Search Menu",
            modifier = Modifier
                .height(19.dp)
                .width(25.dp)
                .clickable(
                    onClick = onBackClick,
                    indication = rememberRipple(
                        bounded = true,
                        color = BLUE05
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        )

        Spacer(modifier = Modifier.width(27.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Color(0x4DFDFDFD), RoundedCornerShape(40.dp))
                .border(1.dp, Color.White, RoundedCornerShape(40.dp))
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                                text = "검색 해보세요!",
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

@Composable
fun SearchFilterToggle(isSelect: Boolean, text: String, onToggleClick: () -> Unit) {
    Box(
        modifier = if (!isSelect) {
            Modifier
                .size(width = 52.dp, height = 24.dp)
                .background(Color(0x4DFFFFFF), RoundedCornerShape(40.dp))
                .border(1.dp, Color.White, RoundedCornerShape(40.dp))
                .clickable(
                    onClick = onToggleClick,
                    indication = rememberRipple(
                        bounded = true,
                        color = BLUE05
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        } else {
            Modifier
                .size(width = 52.dp, height = 24.dp)
                .clickable(
                    onClick = onToggleClick,
                    indication = rememberRipple(
                        bounded = true,
                        color = BLUE05
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        },
        contentAlignment = Alignment.Center
    ) {
        if (isSelect) {
            val backgroundPainter = painterResource(id = R.drawable.ic_search_toggle_bg)
            Image(
                painter = backgroundPainter, // 이미지 리소스 ID
                contentDescription = null, // 배경 이미지이므로 설명은 null
                contentScale = ContentScale.Crop, // 이미지가 박스에 맞게 크롭되도록 설정
                modifier = Modifier.fillMaxSize() // Box의 크기에 맞게 이미지를 채움
            )
        }
        SelectText(isSelect = isSelect, text = text)
    }
}

@Composable
fun SearchFilterBox(selectText: String, filters: List<String>, onSelectFilter: (String) -> Unit) {
    val listSize = filters.size
    require(listSize == 2 || listSize == 4)
    val height = if (listSize == 2) 60.dp else 90.dp

    Box(
        modifier = Modifier.size(width = 60.dp, height = height),
        contentAlignment = Alignment.Center
    ) {

        val backgroundPainter = painterResource(
            if (listSize == 4) {
                R.drawable.ic_search_filter_box_large_bg
            } else {
                R.drawable.ic_search_filter_box_small_bg
            }
        )
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 9.dp),
            userScrollEnabled = false,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // items를 이용해 리스트의 각 아이템을 배치
            items(filters) { text ->
                Box(
                    modifier = Modifier.padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SelectText(
                        isSelect = text == selectText,
                        text = text,
                        modifier = Modifier
                            .clickable(
                                onClick = { onSelectFilter(text) },
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
}


