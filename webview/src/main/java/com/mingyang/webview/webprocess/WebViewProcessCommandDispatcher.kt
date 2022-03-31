package com.mingyang.webview.webprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.mingyang.base.BaseApplication
import com.mingyang.webview.ICallBackMainToWebPInterface
import com.mingyang.webview.IWebViewPToMainPInterface
import com.mingyang.webview.mainprocess.MainProcessCommandService

/**
 * 调度器
 * 通过该类进行 service bind 操作，获取Aidl实现类
 * webview 调用 该类的 executeCommend 方法
 * WebViewProcessCommandDispatcher.executeCommend 执行 AIDL 方法
 * -> IWebViewPToMainPInterface.handleWebCommend 遍历存储的任务指令，找到对应的
 * -> Command.execute 進行解析并执行任務
 */
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

    // 启动服务
    fun initAidlConnection() {
        val intent = Intent(BaseApplication.appContext, MainProcessCommandService::class.java)
        BaseApplication.appContext?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iWebViewPToMainPInterface = IWebViewPToMainPInterface.Stub.asInterface(service)
    }

    // 启动失败，重新启动
    override fun onServiceDisconnected(name: ComponentName?) {
        iWebViewPToMainPInterface = null
        initAidlConnection()
    }

    // 启动失败，重新启动
    override fun onBindingDied(name: ComponentName?) {
        iWebViewPToMainPInterface = null
        initAidlConnection()
    }

    fun executeCommend(commandName: String, params: String, webView: BaseWebView) {
        iWebViewPToMainPInterface?.handleWebCommend(
            commandName,
            params,
            object : ICallBackMainToWebPInterface.Stub() {
                override fun onResult(callbackName: String, respone: String) {
                    webView.handCallBack(callbackName, respone)
                }
            })
    }

}