package com.mingyang.webview.command

interface Command {
    // 方法名
    fun name(): String
    // 执行方法
    fun execute(parameters: Map<*, *>)
}