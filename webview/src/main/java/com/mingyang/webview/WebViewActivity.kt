package com.mingyang.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.mingyang.webview.databinding.ActivityWebViewBinding
import com.mingyang.webview.utils.Constant

class WebViewActivity : AppCompatActivity() {
    private var mDataBind: ActivityWebViewBinding? = null
    private var TAG = WebViewActivity::class.java.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        val url = intent.getStringExtra(Constant.URL)
        val title = intent.getStringExtra(Constant.TITLE)
        mDataBind?.tvTitle?.text = title
        mDataBind?.ivReturn?.setOnClickListener {
            finish()
        }
        // 开启事务并提交
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = WebViewFragment.newInstant(url ?: "")
        transaction.replace(R.id.fl_content, fragment).commit()
    }
}