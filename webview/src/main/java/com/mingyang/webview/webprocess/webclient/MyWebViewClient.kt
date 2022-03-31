package com.mingyang.webview.webprocess.webclient

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mingyang.webview.WebViewCallBack

class MyWebViewClient(
    private val webViewCallBack: WebViewCallBack
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
        webViewCallBack.onLoadStarted(url)
    }

    override fun onPageFinished(view: WebView?, url: String) {
        webViewCallBack.onLoadFinished(url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        webViewCallBack.onError()
    }
}