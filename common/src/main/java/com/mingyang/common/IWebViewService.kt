package com.mingyang.common

import android.content.Context
import androidx.fragment.app.Fragment

interface IWebViewService {
    fun startWebView(context: Context, url: String, title: String, refresh: Boolean)
    fun getWebViewFragment(url: String, refresh: Boolean): Fragment
}