package com.mingyang.webview.webprocess.webchromeclient

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.mingyang.webview.WebViewCallBack

class WebViewDefaultChromeClient(
    private val webViewCallBack: WebViewCallBack
) : WebChromeClient() {
    private val TAG = WebViewDefaultChromeClient::class.java.name


    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        webViewCallBack.updateTitle(title ?: "")
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d(TAG, consoleMessage?.message() ?: "")
        return super.onConsoleMessage(consoleMessage)
    }
}