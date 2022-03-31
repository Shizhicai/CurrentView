package com.mingyang.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 通过服务来进行执命令
 */
class MainProcessCommandService : Service() {

    override fun onBind(intent: Intent): IBinder = MainProcessCommandManager.getInstant()
}