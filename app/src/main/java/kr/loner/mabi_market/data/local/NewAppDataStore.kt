package kr.loner.mabi_market.data.local

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.loner.mabi_market.data.legacy.Item
import kr.loner.mabi_market.data.model.BornBugleFIndKeyword
import kr.loner.mabi_market.data.model.BornBugleWorldChat
import kr.loner.mabi_market.data.model.MillesianUser
import kr.loner.mabi_market.data.model.NewItem
import kr.loner.mabi_market.data.model.ServerType


class NewAppDataStore private constructor(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val gson = Gson()

    private val KEY_FCM_TOKEN = stringPreferencesKey("fcmToken")
    val fcmTokenFlow: Flow<String?>
        get() = context.dataStore.data.map { pref ->
            pref[KEY_FCM_TOKEN] ?: ""
        }

    suspend fun saveFcmToken(fcmToken: String) {
        context.dataStore.edit { pref ->
            pref[KEY_FCM_TOKEN] = fcmToken
        }
    }


    private val KEY_ITEM_LIKE_LIST = stringPreferencesKey("keyItemLikeList")
    val itemLikesFlow: Flow<List<NewItem>>
        get() = context.dataStore.data.map { pref ->
            val json = pref[KEY_ITEM_LIKE_LIST] ?: "[]"
            gson.fromJson(json, object : TypeToken<List<NewItem>>() {}.type)
        }

    suspend fun saveItemLikes(myIdList: List<NewItem>) {
        val json = gson.toJson(myIdList)
        context.dataStore.edit { pref ->
            pref[KEY_ITEM_LIKE_LIST] = json
        }
    }

    private val KEY_BORN_BUGLE_KEYWORD_LIST = stringPreferencesKey("KeyBornBugleKeywordList")
    val bornWorldChatLikeListFlow: Flow<List<BornBugleFIndKeyword>>
        get() = context.dataStore.data.map { pref ->
            val json = pref[KEY_BORN_BUGLE_KEYWORD_LIST] ?: "[]"
            gson.fromJson(json, object : TypeToken<List<BornBugleFIndKeyword>>() {}.type)
        }

    suspend fun saveBornWorldChatLikeListFlow(keywords: List<BornBugleFIndKeyword>) {
        val json = gson.toJson(keywords)
        context.dataStore.edit { pref ->
            pref[KEY_BORN_BUGLE_KEYWORD_LIST] = json
        }
    }


    private val KEY_CUR_SERVER_TYPE = stringPreferencesKey("keyCurServerType")
    val curServerTypeFlow: Flow<ServerType?>
        get() = context.dataStore.data.map { pref ->
            try {
                val json = pref[KEY_CUR_SERVER_TYPE]
                gson.fromJson(json, object : TypeToken<ServerType>() {}.type)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun saveCurServerType(serverType: ServerType) {
        val json = gson.toJson(serverType)
        context.dataStore.edit { pref ->
            pref[KEY_CUR_SERVER_TYPE] = json
        }
    }


    private val KEY_MILLESIAN_USER = stringPreferencesKey("keyMillesianUser")
    val curUserFlow: Flow<MillesianUser?>
        get() = context.dataStore.data.map { pref ->
            try {
                val json = pref[KEY_MILLESIAN_USER]
                gson.fromJson(json, object : TypeToken<MillesianUser>() {}.type)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun saveCurUser(serverType: MillesianUser) {
        val json = gson.toJson(serverType)
        context.dataStore.edit { pref ->
            pref[KEY_MILLESIAN_USER] = json
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: NewAppDataStore? = null

        fun getInstance(context: Context): NewAppDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = NewAppDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }
}







