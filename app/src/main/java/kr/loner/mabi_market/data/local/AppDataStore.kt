package kr.loner.mabi_market.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context){
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val KEY_MY_ID = longPreferencesKey("myId")

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
}







