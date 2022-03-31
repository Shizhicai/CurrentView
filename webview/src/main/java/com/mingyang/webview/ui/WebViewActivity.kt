package com.mingyang.webview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mingyang.webview.R
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
        val refresh = intent.getBooleanExtra(Constant.REFRESH, false)
        mDataBind?.tvTitle?.text = title
        mDataBind?.ivReturn?.setOnClickListener {
            finish()
        }
        // 开启事务并提交
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = WebViewFragment.newInstant(url ?: "", refresh)
        transaction.replace(R.id.fl_content, fragment).commit()
    }

    fun updateTitle(str: String) {
        mDataBind?.tvTitle?.text = str
    }

}