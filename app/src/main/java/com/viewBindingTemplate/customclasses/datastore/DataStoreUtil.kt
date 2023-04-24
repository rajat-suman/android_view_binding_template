package com.viewBindingTemplate.customclasses.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataStoreUtil @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val coRoutineExceptionHandler: CoroutineExceptionHandler,
) {

    /** dataStore*/
    fun <T> saveData(key: Preferences.Key<T>, value: T) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    fun <T> readData(key: Preferences.Key<T>, valueIs: (T?) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit {
                CoroutineScope(Dispatchers.Main).launch {
                    valueIs(it[key])
                }
            }
        }
    }

    fun readAuthToken(authToken: (String?) -> Unit) {
        readData(DatastoreKeys.AUTH_TOKEN) {
            authToken.invoke(it)
        }
    }


    fun isGuestUser(userData: (isGuest : Boolean) -> Unit) {
        readData(DatastoreKeys.IS_GUEST_USER) {
            userData.invoke(it == true)
        }
    }

    fun <T> saveObject(key: Preferences.Key<String>, value: T) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences[key] = Gson().toJson(value)
            }
        }
    }

    fun <T> readObject(key: Preferences.Key<String>, clazz: Class<T>, valueIs: (T?) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit {
                CoroutineScope(Dispatchers.Main).launch {
                    valueIs(Gson().fromJson(it[key], clazz))
                }
            }
        }
    }

    fun clearDataStore(valueIs: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("clearDataStore", "clearDataStore")
                valueIs(true)
//                openActivity(Intent(this@clearDataStore, Login::class.java), true)
            }
        }
    }

    fun <T> removeKey(key: Preferences.Key<T>, onRemove: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
            CoroutineScope(Dispatchers.Main).launch {
                onRemove(true)
            }
        }
    }
}

