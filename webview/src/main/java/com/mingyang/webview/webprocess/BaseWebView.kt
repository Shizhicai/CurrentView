package com.mingyang.webview.webprocess

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.google.gson.Gson
import com.mingyang.webview.WebViewCallBack
import com.mingyang.webview.bean.JsParam
import com.mingyang.webview.webprocess.setting.WebViewDefaultSetting
import com.mingyang.webview.webprocess.webchromeclient.WebViewDefaultChromeClient
import com.mingyang.webview.webprocess.webclient.MyWebViewClient

class BaseWebView : WebView {
    private val TAG = this::class.java.name

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        WebViewDefaultSetting.getInstance().setSettings(this)
        addJavascriptInterface(this, "myWebView")
        WebViewProcessCommandDispatcher.getInstant().initAidlConnection()
    }

    fun registerWebCallBack(webViewCallBack: WebViewCallBack) {
        webViewClient = MyWebViewClient(webViewCallBack)
        webChromeClient = WebViewDefaultChromeClient(webViewCallBack)
    }

    @JavascriptInterface
    fun takeNativeAction(json: String) {
        Log.e(TAG, json)
        if (!TextUtils.isEmpty(json)) {
            val bean = Gson().fromJson(json, JsParam::class.java)
            if (bean != null) {
                if (!TextUtils.isEmpty(bean.name)) {
                    WebViewProcessCommandDispatcher.getInstant()
                        .executeCommend(bean.name, Gson().toJson(bean.param),this)
                }
            }
        }
    }

    /**
     * 回调方法
     */
    fun handCallBack(callBackName: String, response: String) {
        if (!TextUtils.isEmpty(callBackName) && !TextUtils.isEmpty(response))
            post {
                val jsCode = "javascript:xiangxuejs.callback('$callBackName',$response)"
                Log.e("xxx", jsCode)
                evaluateJavascript(jsCode, null)
            }
    }
}