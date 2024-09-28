package kr.loner.mabi_market.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.local.AppDataStore
import kr.loner.mabi_market.data.network.MabinogiApi

class MainViewModel(
    private val mabinogiApi: MabinogiApi,
    private val appDataStore: AppDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState
    fun setSearchText(newText:String){
        _uiState.update { it.copy(searchText = newText) }
    }

    fun search(showToast:()->Unit){
        viewModelScope.launch {
            val text = uiState.value.searchText
            if(text.isEmpty()) return@launch
            //api 대기중
            mabinogiApi
            showToast()
            setSearchText("")
        }
    }

    fun setSelectServer(server:String){
        _uiState.update { it.copy(selectServer = server) }
    }
    fun setSelectTime(time:String){
        _uiState.update { it.copy(selectTime = time) }
    }
    fun setSelectPriceOrder(order:String){
        _uiState.update { it.copy(selectPriceOrder = order) }
    }

    fun isVisibleToggleServer(isVisible:Boolean){
        _uiState.update { it.copy(isVisibleToggleServer = isVisible) }
    }
    fun isVisibleToggleTime(isVisible:Boolean){
        _uiState.update { it.copy(isVisibleToggleTime = isVisible) }
    }
    fun isVisibleTogglePriceOrder(isVisible:Boolean){
        _uiState.update { it.copy(isVisibleTogglePriceOrder = isVisible) }
    }

    data class MainUiState(
        val searchText:String = "",
        val selectServer:String? = null,
        val selectTime:String? = null,
        val selectPriceOrder:String? = null,

        val isVisibleToggleServer:Boolean = false,
        val isVisibleToggleTime:Boolean = false,
        val isVisibleTogglePriceOrder:Boolean = false,
    )
}

