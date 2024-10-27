package kr.loner.mabi_market.feature.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.loner.mabi_market.data.ServiceLocator
import kr.loner.mabi_market.data.network.FirebaseCollection
import kr.loner.mabi_market.feature.main.NewMainViewModel

class SplashViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val dataStore = ServiceLocator.getNewDataStore(context)
    private val userDb = ServiceLocator.getFireStoreDB(FirebaseCollection.USER)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(dataStore,userDb) as T
        }
        throw IllegalArgumentException("Unknown MainViewModel Class")
    }
}