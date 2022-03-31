package com.mingyang.base

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}