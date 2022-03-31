package com.mingyang.currentview.widget

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.auto.service.AutoService
import com.mingyang.base.BaseApplication
import com.mingyang.webview.ICallBackMainToWebPInterface
import com.mingyang.webview.command.Command

@AutoService(value = [Command::class])
class CommonShowToast : Command {
    override fun name(): String = "showToast"

    override fun execute(parameters: Map<*, *>, callBack: ICallBackMainToWebPInterface?) {
        val message = parameters["message"].toString()
        Log.e("CommonShowToast", message)
        if (!TextUtils.isEmpty(message)) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                Toast.makeText(BaseApplication.appContext, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}