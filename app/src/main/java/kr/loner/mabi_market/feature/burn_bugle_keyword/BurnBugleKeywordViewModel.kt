package kr.loner.mabi_market.feature.burn_bugle_keyword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.local.NewAppDataStore
import kr.loner.mabi_market.data.model.BornBugleFIndKeyword
import kr.loner.mabi_market.data.model.ServerType


class BurnBugleKeywordViewModel(
    private val newAppDataStore: NewAppDataStore,
    private val keywordDb: CollectionReference
) : ViewModel() {
    private val _uiState = MutableStateFlow(BurnBugleKeywordUiState())
    val uiState: StateFlow<BurnBugleKeywordUiState> = _uiState

    private val curServer = newAppDataStore.curServerTypeFlow.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ServerType.RYUTE
    )

    fun addLikeKeyword(
        completeMsg: (String) -> Unit,
        failMsg: (String) -> Unit
    ) = viewModelScope.launch {
        val keyword = uiState.value.keyword
        if(keyword.isNullOrEmpty()) return@launch

        val user = newAppDataStore.curUserFlow.first() ?: return@launch
        val curServer = curServer.value ?: ServerType.RYUTE
        val keywordData = BornBugleFIndKeyword(keyword, curServer, user.id)

        val saveLogic = viewModelScope.launch {
            newAppDataStore.bornWorldChatLikeListFlow.collectLatest { keywords ->
                newAppDataStore.saveBornWorldChatLikeListFlow(keywords.plus(keywordData))
                keywordDb
                    .add(keywordData)
                    .addOnSuccessListener {
                        setKeyword(null)
                        completeMsg("키워드가 등록되었습니다.")
                    }
                    .addOnFailureListener { failMsg("키워드 등록 실패") }
            }
        }

        keywordDb
            .whereEqualTo("keyword", keywordData.keyword)
            .whereEqualTo("serverType", keywordData.serverType)
            .whereEqualTo("userId", keywordData.userId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    saveLogic.start()
                } else {
                    setKeyword(null)
                    failMsg("이미 존재하는 키워드 입니다.")
                }
            }.addOnFailureListener {
                failMsg("키워드 등록 실패")
            }
    }

    fun setKeyword(keyword: String?){
        _uiState.update { it.copy(keyword = keyword) }
    }

    data class BurnBugleKeywordUiState(
        val keyword: String? = null
    )
}