package com.mingyang.webview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mingyang.base.loadsir.ErrorCallback
import com.mingyang.base.loadsir.LoadingCallback
import com.mingyang.webview.R
import com.mingyang.webview.WebViewCallBack
import com.mingyang.webview.databinding.FragmentWebViewBinding
import com.mingyang.webview.webprocess.setting.WebViewDefaultSetting
import com.mingyang.webview.utils.Constant
import com.mingyang.webview.webprocess.webchromeclient.WebViewDefaultChromeClient
import com.mingyang.webview.webprocess.webclient.MyWebViewClient
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class WebViewFragment : Fragment(), WebViewCallBack, Callback.OnReloadListener, OnRefreshListener {
    private var mBindData: FragmentWebViewBinding? = null
    private var mLoadService: LoadService<Any>? = null
    private var mIsError: Boolean = false
    private var mIsRefresh: Boolean = true
    private val TAG: String = WebViewFragment::class.java.name

    companion object {
        fun newInstant(url: String, refresh: Boolean): WebViewFragment {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString(Constant.URL, url)
            bundle.putBoolean(Constant.REFRESH, refresh)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindData = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false)
        mBindData?.apply {
            webView.registerWebCallBack(this@WebViewFragment)
            mIsRefresh = arguments?.getBoolean(Constant.REFRESH) == true
            mLoadService = LoadSir.getDefault().register(root, this@WebViewFragment)
            srl.setOnRefreshListener(this@WebViewFragment)
            srl.setEnableLoadMore(false)
            srl.setEnableRefresh(mIsRefresh)
            arguments?.getString(Constant.URL, "")?.let { webView.loadUrl(it) }
        }
        return mLoadService?.loadLayout
    }

    // 开始加载
    override fun onLoadStarted(url: String) {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    // 加载结束 -- 成功。失败都会回调到这里
    override fun onLoadFinished(url: String) {
        if (mIsError) {
            mBindData?.srl?.setEnableRefresh(true)
        } else {
            mBindData?.srl?.setEnableRefresh(mIsRefresh)
        }
        // 错误最终回调这里
        Log.e(TAG, "onLoadFinished")
        mBindData?.srl?.finishRefresh()
        if (mIsError) {
            mLoadService?.showCallback(ErrorCallback::class.java)
        } else {
            mLoadService?.showSuccess()
        }
        mIsError = false
    }

    // 错误
    override fun onError() {
        mIsError = true
    }

    override fun updateTitle(title: String) {
        if (activity is WebViewActivity) {
            (activity as WebViewActivity).updateTitle(title)
        }
    }

    // 点击重试
    override fun onReload(v: View?) {
        mLoadService?.showCallback(LoadingCallback::class.java)
        mBindData?.webView?.reload()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mBindData?.webView?.reload()
    }

}