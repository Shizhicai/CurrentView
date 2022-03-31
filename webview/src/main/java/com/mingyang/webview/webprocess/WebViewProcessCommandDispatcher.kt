package com.mingyang.webview.webprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.mingyang.base.BaseApplication
import com.mingyang.webview.IWebViewPToMainPInterface
import com.mingyang.webview.mainprocess.MainProcessCommandService

class WebViewProcessCommandDispatcher private constructor() : ServiceConnection {
    private var iWebViewPToMainPInterface: IWebViewPToMainPInterface? = null

    companion object {
        private var dispatcher: WebViewProcessCommandDispatcher? = null
        fun getInstant(): WebViewProcessCommandDispatcher {
            if (dispatcher == null) {
                synchronized(WebViewProcessCommandDispatcher::class.java) {
                    if (dispatcher == null) {
                        dispatcher = WebViewProcessCommandDispatcher()
                    }
                }
            }
            return dispatcher!!
        }
    }

    fun initAidlConnection() {
        val intent = Intent(BaseApplication.appContext, MainProcessCommandService::class.java)
        BaseApplication.appContext?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iWebViewPToMainPInterface = IWebViewPToMainPInterface.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        iWebViewPToMainPInterface = null
        initAidlConnection()
    }

    override fun onBindingDied(name: ComponentName?) {
        iWebViewPToMainPInterface = null
        initAidlConnection()
    }

    fun executeCommend(commandName: String, params: String) {
        iWebViewPToMainPInterface?.handleWebCommend(commandName, params)
    }

}