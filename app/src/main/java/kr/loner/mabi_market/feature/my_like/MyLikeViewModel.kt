package kr.loner.mabi_market.feature.my_like

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.Item
import kr.loner.mabi_market.data.ItemServerType
import kr.loner.mabi_market.data.local.AppDataStore

class MyLikeViewModel(
    private val appDataStore: AppDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyLikeUiState())
    val uiState: StateFlow<MyLikeUiState> = _uiState

    private val itemLikeList = appDataStore.itemLikesFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )
    val itemLikeFilters = itemLikeList.combine(uiState) { items, state ->
        val filters = items.filter { it.serverType == state.selectServer }
            .filter { it.name.contains(state.searchText, ignoreCase = true) }

        when {
            state.selectTime == SelectTimeType.DESC &&
                    state.selectPriceOrder == SelectPriceType.DESC -> {
                // 최신순 정렬 (registerTimeStamp 최신순, price 많은순)
                filters.sortedWith(
                    compareByDescending<Item> { it.registerTimeStamp }
                        .thenByDescending { it.price }
                )
            }

            state.selectTime == SelectTimeType.ASC &&
                    state.selectPriceOrder == SelectPriceType.ASC -> {
                // 오래된순 정렬 (registerTimeStamp 오래된순, price 적은순)
                filters.sortedWith(
                    compareBy<Item> { it.registerTimeStamp }
                        .thenBy { it.price }
                )
            }


            state.selectTime == SelectTimeType.DESC &&
                    state.selectPriceOrder == SelectPriceType.ASC -> {
                filters.sortedWith(
                    compareByDescending<Item> { it.registerTimeStamp }
                        .thenBy { it.price }
                )
            }


            state.selectTime == SelectTimeType.ASC &&
                    state.selectPriceOrder == SelectPriceType.DESC -> {
                // 오래된순 정렬 (registerTimeStamp 오래된순, price 많은순)
                filters.sortedWith(
                    compareBy<Item> { it.registerTimeStamp }
                        .thenByDescending { it.price }
                )
            }
            state.selectTime == SelectTimeType.DESC &&
                    state.selectPriceOrder == SelectPriceType.DESC -> {
                // 최신순 정렬 (registerTimeStamp 최신순, price 많은순)
                filters.sortedWith(
                    compareByDescending<Item> { it.registerTimeStamp }
                        .thenByDescending { it.price }
                )
            }

            state.selectTime == SelectTimeType.ASC &&
                    state.selectPriceOrder == SelectPriceType.ASC -> {
                // 오래된순 정렬 (registerTimeStamp 오래된순, price 적은순)
                filters.sortedWith(
                    compareBy<Item> { it.registerTimeStamp }
                        .thenBy { it.price }
                )
            }


            state.selectTime == SelectTimeType.DESC &&
                    state.selectPriceOrder == SelectPriceType.ASC -> {
                filters.sortedWith(
                    compareByDescending<Item> { it.registerTimeStamp }
                        .thenBy { it.price }
                )
            }


            state.selectTime == SelectTimeType.ASC &&
                    state.selectPriceOrder == SelectPriceType.DESC -> {
                // 오래된순 정렬 (registerTimeStamp 오래된순, price 많은순)
                filters.sortedWith(
                    compareBy<Item> { it.registerTimeStamp }
                        .thenByDescending { it.price }
                )
            }

            else -> filters
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setSearchText(newText: String) {
        _uiState.update { it.copy(searchText = newText) }
    }
    fun setSelectServer(server: String) {
        val serverType = when (server) {
            ItemServerType.RYUTE.desc -> ItemServerType.RYUTE
            ItemServerType.MANDOLIN.desc -> ItemServerType.MANDOLIN
            ItemServerType.HARF.desc -> ItemServerType.HARF
            else -> ItemServerType.WOLF
        }
        _uiState.update { it.copy(selectServer = serverType) }
    }

    fun setSelectTime(time: String) {
        val timeType = when (time) {
            SelectTimeType.ASC.desc -> SelectTimeType.ASC
            else -> SelectTimeType.DESC
        }
        _uiState.update { it.copy(selectTime = timeType) }
    }

    fun setSelectPriceOrder(order: String) {
        val orderType = when (order) {
            SelectPriceType.ASC.desc -> SelectPriceType.ASC
            else -> SelectPriceType.DESC
        }
        _uiState.update { it.copy(selectPriceOrder = orderType) }
    }

    fun isVisibleToggleServer(isVisible: Boolean) {
        _uiState.update { it.copy(isVisibleToggleServer = isVisible) }
    }

    fun isVisibleToggleTime(isVisible: Boolean) {
        _uiState.update { it.copy(isVisibleToggleTime = isVisible) }
    }

    fun isVisibleTogglePriceOrder(isVisible: Boolean) {
        _uiState.update { it.copy(isVisibleTogglePriceOrder = isVisible) }
    }

    fun setIsMyLike(item: Item) = viewModelScope.launch {
        val itemList = itemLikeList.value
        val find = itemList.find { it.id == item.id }
        appDataStore.saveItemLikes(
            if (find == null) {
                itemList.plus(item)
            } else {
                itemList.minus(find)
            }
        )
    }

    data class MyLikeUiState(
        val searchText: String = "",

        val selectServer: ItemServerType = ItemServerType.RYUTE,
        val selectTime: SelectTimeType? = null,
        val selectPriceOrder: SelectPriceType? = null,

        val isVisibleToggleServer: Boolean = false,
        val isVisibleToggleTime: Boolean = false,
        val isVisibleTogglePriceOrder: Boolean = false,
    )

    enum class SelectTimeType(val desc: String) {
        //내림차순 3,2,1..
        DESC("최신 순"),

        //오름차순 1,2,3..
        ASC("오래된 순")

    }

    enum class SelectPriceType(val desc: String) {
        //내림차순 3,2,1..
        DESC("가격↑"),

        //오름차순 1,2,3..
        ASC("가격↓")

    }


}

