package com.mingyang.webview

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import com.mingyang.common.IWebViewService
import com.mingyang.webview.ui.WebViewActivity
import com.mingyang.webview.ui.WebViewFragment
import com.mingyang.webview.utils.Constant

@AutoService(value = [IWebViewService::class])
class WebViewServiceImpl : IWebViewService {

    override fun startWebView(context: Context, url: String, title: String, refresh: Boolean) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(Constant.URL, url)
        intent.putExtra(Constant.TITLE, title)
        intent.putExtra(Constant.REFRESH, refresh)
        context.startActivity(intent)
    }

    override fun getWebViewFragment(url: String, refresh: Boolean): Fragment {
        return WebViewFragment.newInstant(url, refresh)
    }
}