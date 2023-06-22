package com.viewBindingTemplate.customclasses.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object DatastoreKeys {

    private const val APPLICATION_ID = "DataStore_Application_ID"//BuildConfig.APPLICATION_ID

    //    const val DATA_STORE_NAME = "DATA_STORE_${BuildConfig.APPLICATION_ID}"  While developing the application enable this and remove below one
    const val DATA_STORE_NAME = "DATA_STORE_Application_ID"

    val AUTH_TOKEN by lazy { stringPreferencesKey("${APPLICATION_ID}_AUTH_TOKEN") }
    val USER_DATA by lazy { stringPreferencesKey("${APPLICATION_ID}_USER_DATA") }
    val REFRESH_TOKEN by lazy { stringPreferencesKey("${APPLICATION_ID}_REFRESH_TOKEN") }
    val REMEMBER by lazy { booleanPreferencesKey("${APPLICATION_ID}_REMEMBER") }
    val DEVICE_TOKEN by lazy { stringPreferencesKey("${APPLICATION_ID}_DEVICE_TOKEN") }
    val IS_GUEST_USER by lazy { booleanPreferencesKey("${APPLICATION_ID}_IS_GUEST_USER") }
    val LANGUAGE by lazy { stringPreferencesKey("${APPLICATION_ID}_LANGUAGE") }
}
