package com.mingyang.webview.mainprocess

import com.google.gson.Gson
import com.mingyang.webview.IWebViewPToMainPInterface
import com.mingyang.webview.command.Command
import java.util.*
import kotlin.collections.HashMap

/**
 * 主线管理器
 */
class MainProcessCommandManager : IWebViewPToMainPInterface.Stub {
    // 存储方法名称，和命令实体
    private val mCommands: HashMap<String, Command> = HashMap()

    companion object {
        private var mpcm: MainProcessCommandManager? = null
        fun getInstant(): MainProcessCommandManager {
            if (mpcm == null) {
                synchronized(MainProcessCommandManager::class.java) {
                    if (mpcm == null) {
                        mpcm = MainProcessCommandManager()
                    }
                }
            }
            return mpcm!!
        }
    }

    private constructor() {
        val commandImpl = ServiceLoader.load(Command::class.java)
        for (command in commandImpl) {
            if (!mCommands.containsKey(command.name())) {
                mCommands[command.name()] = command
            }
        }
    }

    fun executeCommand(commandName: String, params: Map<*, *>) {
        mCommands[commandName]?.execute(params)
    }

    override fun handleWebCommend(commandName: String, jsonParams: String) {
        // TODO ？为什么要回调
        getInstant().executeCommand(commandName, Gson().fromJson(jsonParams, Map::class.java))
    }
}