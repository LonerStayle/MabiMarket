package kr.loner.mabi_market.feature

import androidx.lifecycle.ViewModel
import kr.loner.mabi_market.data.local.AppDataStore
import kr.loner.mabi_market.data.network.MabinogiApi
import kr.loner.mabi_market.data.network.RemoteModule

class MainViewModel(
    private val mabinogiApi: MabinogiApi,
    private val appDataStore: AppDataStore,
) : ViewModel() {

}

