package kr.loner.mabi_market.feature.main

import android.util.Log
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
import kr.loner.mabi_market.data.ServerType
import kr.loner.mabi_market.data.local.AppDataStore
import kr.loner.mabi_market.data.network.MabinogiApi
import kr.loner.mabi_market.util.formattedDate

class MainViewModel(
    private val mabinogiApi: MabinogiApi,
    private val appDataStore: AppDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    private val itemLikeList = appDataStore.itemLikesFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    private val _searchItemList = MutableStateFlow(Item.MUCK_DATA)
    val searchItemList: StateFlow<List<Item>> =
        _searchItemList.combine(itemLikeList) { searchList, likeList ->
            searchList.map { item ->
                val find = likeList.find { it.id == item.id }
                item.copy(myLike = find != null)
            }
        }.combine(uiState) { filterList, state ->
            filterList.filter { it.serverType == state.selectServer }
        }.stateIn(
            viewModelScope, SharingStarted.Eagerly, emptyList()
        )

    val searchRecords = appDataStore.searchRecordsFlow.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    init {
        viewModelScope.launch {
            val historyGroupRes = mabinogiApi.getBornBugleWorldHistories(ServerType.RYUTE.desc)
            Log.d("checkk", historyGroupRes.toString())
            Log.d("checkk00", historyGroupRes.hornBugleWorldHistory[0].dateSend.formattedDate)
        }

    }

    fun setSearchText(newText: String) {
        _uiState.update { it.copy(searchText = newText) }
    }

    fun search(searchComplete: () -> Unit) {
        viewModelScope.launch {
            val text = uiState.value.searchText
            if (text.isEmpty()) return@launch
            //api 대기중
            mabinogiApi
            searchComplete()
            setSearchRecord(text)
            setSearchText("")
            _uiState.update { it.copy(searchCompletedTextOrHint = text) }
        }
    }

    fun setSelectServer(server: String, onShowToast: () -> Unit) {
        val serverType = when (server) {
            ServerType.RYUTE.desc -> ServerType.RYUTE
            ServerType.MANDOLIN.desc -> ServerType.MANDOLIN
            ServerType.HARF.desc -> ServerType.HARF
            else -> ServerType.WOLF
        }
        _uiState.update { it.copy(selectServer = serverType) }
    }

    fun setSelectTime(time: String, onShowToast: () -> Unit) {
        val timeType = when (time) {
            SelectTimeType.ASC.desc -> SelectTimeType.ASC
            else -> SelectTimeType.DESC
        }
        onShowToast()
        _uiState.update { it.copy(selectTime = timeType) }
    }

    fun setSelectPriceOrder(order: String, onShowToast: () -> Unit) {
        val orderType = when (order) {
            SelectPriceType.ASC.desc -> SelectPriceType.ASC
            else -> SelectPriceType.DESC
        }
        onShowToast()
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
                itemList.plus(item.copy(myLike = true))
            } else {
                itemList.minus(find)
            }
        )
    }

    fun setFilterText(text: String) {
        _uiState.update { it.copy(searchFilterText = text) }
    }

    fun setSearchRecord(searchText: String) = viewModelScope.launch {
        val records = searchRecords.value
        val find = records.find { it == searchText }
        appDataStore.saveSearchRecordsFlow(
            if (find == null) {
                records.plus(searchText)
            } else {
                records.minus(find)
            }
        )
    }

    data class MainUiState(
        val searchText: String = "",
        val searchCompletedTextOrHint: String = "",

        val searchFilterText: String = "",

        val selectServer: ServerType = ServerType.RYUTE,
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

