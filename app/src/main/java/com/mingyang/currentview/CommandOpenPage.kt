package com.mingyang.currentview

import android.content.ComponentName
import android.content.Intent
import android.text.TextUtils
import com.google.auto.service.AutoService
import com.mingyang.base.BaseApplication
import com.mingyang.webview.command.Command

@AutoService(value = [Command::class])
class CommandOpenPage : Command {
    override fun name(): String = "openPage"

    override fun execute(parameters: Map<*, *>) {
        val className = parameters["target_class"].toString()
        if (!TextUtils.isEmpty(className)) {
            val intent = Intent()
            intent.component = ComponentName(BaseApplication.appContext!!, className)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            BaseApplication.appContext?.startActivity(intent)
        }
    }
}