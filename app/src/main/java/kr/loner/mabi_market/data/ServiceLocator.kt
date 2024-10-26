package kr.loner.mabi_market.data

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kr.loner.mabi_market.data.local.NewAppDataStore
import kr.loner.mabi_market.data.local.legacy.AppDataStore
import kr.loner.mabi_market.data.network.FcmApi
import kr.loner.mabi_market.data.network.FirebaseCollection
import kr.loner.mabi_market.data.network.MabinogiApi
import kr.loner.mabi_market.data.network.RemoteModule

object ServiceLocator {

    private var mabinogiApi: MabinogiApi? = null
    private var fcmApi: FcmApi? = null
    fun getMabinogiApi() = mabinogiApi ?: RemoteModule.getMabinogiApi().also {
        mabinogiApi = it
    }
    fun getFcmApi() = fcmApi ?: RemoteModule.getFcmApi().also {
        fcmApi = it
    }

    fun getDataStore(context: Context) = AppDataStore.getInstance(context)
    fun getNewDataStore(context: Context) = NewAppDataStore.getInstance(context)

    fun getFireStoreDB(collection: FirebaseCollection): CollectionReference = FirebaseFirestore.getInstance()
        .collection(collection.path)

}