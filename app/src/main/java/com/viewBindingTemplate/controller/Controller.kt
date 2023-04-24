package com.viewBindingTemplate.controller

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.viewBindingTemplate.remote.ConnectionManager
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class Controller : Application(), ActivityLifecycleCallbacks {

    companion object {
        var context: WeakReference<Context>? = null
    }

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        registerActivityLifecycleCallbacks(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ConnectionManager.init(applicationContext)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        context = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        context = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        context = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        context = WeakReference(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        context = WeakReference(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle)= Unit

    override fun onActivityDestroyed(activity: Activity) {
        context = null
        unregisterActivityLifecycleCallbacks(this)
    }

}