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
                if ("showToast" == bean.name) {
                    Toast.makeText(
                        context,
                        Gson().fromJson(bean.param, Map::class.java)["message"].toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}