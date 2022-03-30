package com.mingyang.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mingyang.webview.databinding.FragmentWebViewBinding
import com.mingyang.webview.utils.Constant
import com.mingyang.webview.webclient.MyWebViewClient

class WebViewFragment : Fragment(), WebViewCallBack {
    var mBindData: FragmentWebViewBinding? = null

    companion object {
        fun newInstant(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString(Constant.URL, url)
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
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = MyWebViewClient(this@WebViewFragment)
            arguments?.getString(Constant.URL, "")?.let { webView.loadUrl(it) }
        }
        return mBindData?.root
    }

    override fun onLoadStarted(url: String) {

    }

    override fun onLoadFinished(url: String) {

    }

    override fun onError() {

    }
}