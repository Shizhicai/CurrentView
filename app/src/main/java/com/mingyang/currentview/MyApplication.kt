package com.mingyang.currentview

import com.kingja.loadsir.core.LoadSir
import com.mingyang.base.BaseApplication
import com.mingyang.base.loadsir.*


class MyApplication : BaseApplication() {

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