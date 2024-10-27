package kr.loner.mabi_market.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.local.NewAppDataStore
import kr.loner.mabi_market.data.model.MabiUser
import java.util.UUID

class SplashViewModel(
    private val newAppDataStore: NewAppDataStore,
    private val userDb: CollectionReference
): ViewModel() {
    private var isTokenSuccessd = false
    fun init() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(isTokenSuccessd) return@addOnCompleteListener
            isTokenSuccessd = task.isSuccessful
            if (task.isSuccessful) {
                viewModelScope.launch {
                    val token = task.result
                    newAppDataStore.saveFcmToken(token)
                    val user = newAppDataStore.curUserFlow.first()
                    if (user == null) {
                        val randomId = UUID.randomUUID().toString()
                        val newUser = MabiUser(randomId, token)
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

}