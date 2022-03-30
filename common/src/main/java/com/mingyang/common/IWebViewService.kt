package com.mingyang.common

import android.content.Context
import androidx.fragment.app.Fragment

interface IWebViewService {
    fun startWebView(context: Context, url: String, title: String)
    fun getWebViewFragment(url: String): Fragment
}