package com.mingyang.webview

interface WebViewCallBack {
    fun onLoadStarted(url: String)
    fun onLoadFinished(url: String)
    fun onError()
    fun updateTitle(title: String)
}