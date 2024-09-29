package kr.loner.mabi_market.data

import android.content.Context
import kr.loner.mabi_market.data.local.AppDataStore
import kr.loner.mabi_market.data.network.MabinogiApi
import kr.loner.mabi_market.data.network.RemoteModule

object ServiceLocator {

    private var mabinogiApi: MabinogiApi? = null
    fun getMabinogiApi() = mabinogiApi ?: RemoteModule.getMabinogiApi().also {
        mabinogiApi = it
    }

    fun getDataStore(context: Context) = AppDataStore.getInstance(context)
}