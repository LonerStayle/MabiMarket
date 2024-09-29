package kr.loner.mabi_market.feature.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.loner.mabi_market.data.ServiceLocator

class MainViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val mabinogiApi = ServiceLocator.getMabinogiApi()
    private val dataStore = ServiceLocator.getDataStore(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mabinogiApi,dataStore) as T
        }
        throw IllegalArgumentException("Unknown MainViewModel Class")
    }
}