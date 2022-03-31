package com.mingyang.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 通过服务来进行执命令
 * 建立绑定效果而已
 */
class MainProcessCommandService : Service() {

    override fun onBind(intent: Intent): IBinder = MainProcessCommandManager.getInstant()
}