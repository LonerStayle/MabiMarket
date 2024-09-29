package kr.loner.mabi_market.feature.my_like

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.loner.mabi_market.data.ServiceLocator

class MyLikeViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val dataStore = ServiceLocator.getDataStore(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyLikeViewModel::class.java)){
            return MyLikeViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown MainViewModel Class")
    }
}