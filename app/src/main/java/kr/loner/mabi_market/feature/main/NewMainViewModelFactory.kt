package kr.loner.mabi_market.feature.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.loner.mabi_market.data.ServiceLocator
import kr.loner.mabi_market.data.network.FirebaseCollection

class NewMainViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val mabinogiApi = ServiceLocator.getMabinogiApi()
    private val dataStore = ServiceLocator.getNewDataStore(context)
    private val userDb = ServiceLocator.getFireStoreDB(FirebaseCollection.USER)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMainViewModel::class.java)) {
            return NewMainViewModel(mabinogiApi, dataStore,userDb) as T
        }
        throw IllegalArgumentException("Unknown MainViewModel Class")
    }
}