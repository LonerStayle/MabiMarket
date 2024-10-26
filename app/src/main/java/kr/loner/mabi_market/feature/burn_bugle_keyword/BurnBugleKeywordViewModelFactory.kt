package kr.loner.mabi_market.feature.burn_bugle_keyword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.loner.mabi_market.data.ServiceLocator
import kr.loner.mabi_market.data.network.FirebaseCollection

class BurnBugleKeywordViewModelFactory(context: Context):ViewModelProvider.Factory {
    private val dataStore = ServiceLocator.getNewDataStore(context)
    private val keywordDb = ServiceLocator.getFireStoreDB(FirebaseCollection.KEYWORD)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BurnBugleKeywordViewModel::class.java)) {
            return BurnBugleKeywordViewModel(dataStore,keywordDb) as T
        }
        throw IllegalArgumentException("Unknown MainViewModel Class")
    }
}