package kr.loner.mabi_market.data.local.legacy

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


class AppDataStore private constructor(private val context: Context){
    private val KEY_MY_ID = longPreferencesKey("myId")
    private val KEY_ITEM_LIKE_LIST = stringPreferencesKey("keyItemLikeList")
    private val KEY_SEARCH_RECORD_LIST = stringPreferencesKey("keySearchRecordList")

    private val Context.dataStore by preferencesDataStore(name = "settings")

    val myIdFlow: Flow<Long>
        get() = context.dataStore.data.map { pref ->
            pref[KEY_MY_ID] ?: 0
        }

    suspend fun saveMyId(myId: Long, saveSuccessCallback: (Long) -> Unit = {}) {
        context.dataStore.edit { pref ->
            pref[KEY_MY_ID] = myId
            saveSuccessCallback(myId)
        }
    }


    private val gson = Gson()
    val itemLikesFlow: Flow<List<Item>>
        get() = context.dataStore.data.map { pref ->
            val json = pref[KEY_ITEM_LIKE_LIST] ?: "[]"
            gson.fromJson(json, object : TypeToken<List<Item>>() {}.type)
        }


    suspend fun saveItemLikes(myIdList: List<Item>) {
        val json = gson.toJson(myIdList)
        context.dataStore.edit { pref ->
            pref[KEY_ITEM_LIKE_LIST] = json
        }
    }


    val searchRecordsFlow: Flow<List<String>>
        get() = context.dataStore.data.map { pref ->
            val json = pref[KEY_SEARCH_RECORD_LIST] ?: "[]"
            gson.fromJson(json, object : TypeToken<List<String>>() {}.type)
        }

    suspend fun saveSearchRecordsFlow(records: List<String>) {
        val json = gson.toJson(records)
        context.dataStore.edit { pref ->
            pref[KEY_SEARCH_RECORD_LIST] = json
        }
    }
    companion object {

        @Volatile
        private var INSTANCE: AppDataStore? = null

        fun getInstance(context: Context): AppDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = AppDataStore(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}







