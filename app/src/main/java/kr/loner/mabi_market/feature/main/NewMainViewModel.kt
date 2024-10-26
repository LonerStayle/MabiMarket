package kr.loner.mabi_market.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.local.NewAppDataStore
import kr.loner.mabi_market.data.model.BornBugleFIndKeyword
import kr.loner.mabi_market.data.model.MillesianUser
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

    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModelScope.launch {
                    val token = task.result
                    newAppDataStore.saveFcmToken(token)

                    val user = newAppDataStore.curUserFlow.first()
                    if (user == null) {
                        val randomId = UUID.randomUUID().toString()
                        val newUser = MillesianUser(randomId, token)
                        userDb.document(randomId).set(newUser)
                        newAppDataStore.saveCurUser(newUser)
                    } else {
                        val updateUser = user.copy(fcmToken = task.result)
                        newAppDataStore.saveCurUser(updateUser)
                        userDb.document(updateUser.id).set(updateUser)
                    }
                }
            }
        }
    }

    data class NewMainUiState(
        val keywordList: List<BornBugleFIndKeyword> = emptyList(),
        val curApiType: ApiType = ApiType.BornBugle,

        val bornBugleCurServer: ServerType = ServerType.RYUTE,
        val auctionCurServer: ServerType = ServerType.RYUTE
    )

    enum class ApiType {
        BornBugle,
        Auction
    }
}
