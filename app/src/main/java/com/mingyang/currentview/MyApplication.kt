package com.mingyang.currentview

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.mingyang.base.loadsir.*


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLoad()
    }

    private fun initLoad() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .addCallback(CustomCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }
}