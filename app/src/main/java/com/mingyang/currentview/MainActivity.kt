package com.mingyang.currentview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mingyang.base.BaseServiceLoader
import com.mingyang.common.IWebViewService
import com.mingyang.currentview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mDataBind: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mDataBind?.apply {
            btnWebview.setOnClickListener {
                val webService = BaseServiceLoader.load(IWebViewService::class.java)
                webService?.startWebView(this@MainActivity, "file:///android_asset/demo.html", "测试", true)
            }
        }
    }
}