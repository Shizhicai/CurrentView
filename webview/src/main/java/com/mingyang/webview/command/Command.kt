package com.mingyang.webview.command

import com.mingyang.webview.ICallBackMainToWebPInterface

/**
 * 指令基类
 */
interface Command {
    // 方法名
    fun name(): String

    // 执行方法
    fun execute(parameters: Map<*, *>, callBack: ICallBackMainToWebPInterface?)
}