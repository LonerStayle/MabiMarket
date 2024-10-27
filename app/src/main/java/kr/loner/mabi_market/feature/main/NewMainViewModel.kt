package kr.loner.mabi_market.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.local.NewAppDataStore
import kr.loner.mabi_market.data.model.BornBugleFIndKeyword
import kr.loner.mabi_market.data.model.BornBugleWorldChat
import kr.loner.mabi_market.data.model.MabiUser
import kr.loner.mabi_market.data.model.NewItem
import kr.loner.mabi_market.data.model.ServerType
import kr.loner.mabi_market.data.network.MabinogiApi
import java.util.UUID

class NewMainViewModel(
    private val mabinogiApi: MabinogiApi,
    private val newAppDataStore: NewAppDataStore,
    private val userDb: CollectionReference

) : ViewModel() {
    private val _uiState = MutableStateFlow(NewMainUiState())
    val uiState: StateFlow<NewMainUiState> = _uiState

    val curServerType = newAppDataStore.curServerTypeFlow.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ServerType.RYUTE
    )

    private val _bornList = MutableStateFlow<List<BornBugleWorldChat>>(emptyList())
    val bornList: StateFlow<List<BornBugleWorldChat>> = _bornList

    private val _auctionList = MutableStateFlow<List<NewItem>>(emptyList())
    val auctionList: StateFlow<List<NewItem>> = _auctionList

    init {
        viewModelScope.launch {

//            _auctionList.value = mabinogiApi.getAuctionList("롱 소드").auctionItems

            _bornList.value = mabinogiApi.getBornBugleWorldHistories(
                curServerType.value?.desc ?: ServerType.RYUTE.desc
            ).hornBugleWorldHistory
        }
    }


    fun showApiTypeList(isShow: Boolean) {
        _uiState.update { it.copy(showApiTypeList = isShow) }
    }

    fun selectApiType(type: ApiType) {
        _uiState.update { it.copy(curApiType = type) }
    }

    fun selectCurServer(serverType: ServerType) = viewModelScope.launch {
        newAppDataStore.saveCurServerType(serverType)

        _bornList.value = mabinogiApi
            .getBornBugleWorldHistories(serverType.desc)
            .hornBugleWorldHistory
    }

    data class NewMainUiState(
        val keywordList: List<BornBugleFIndKeyword> = emptyList(),
        val curApiType: ApiType = ApiType.BornBugle,
        val showApiTypeList: Boolean = false
    )

    enum class ApiType(val desc: String) {
        BornBugle("거대한 외침의 뿔피리"),
        Auction("경매장")
    }
}
