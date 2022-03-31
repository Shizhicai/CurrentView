package com.mingyang.currentview

import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.mingyang.webview.ICallBackMainToWebPInterface
import com.mingyang.webview.command.Command

@AutoService(value = [Command::class])
class CommandLogin : Command {
    override fun name(): String = "login"

    override fun execute(parameters: Map<*, *>, callBack: ICallBackMainToWebPInterface?) {
        val map = HashMap<String, String>()
        map["accountName"] = "test1123"
        callBack?.onResult(parameters.get("callbackname").toString(), Gson().toJson(map))
    }
}