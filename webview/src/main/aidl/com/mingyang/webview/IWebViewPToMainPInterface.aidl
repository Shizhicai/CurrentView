// IWebViewPToMainPInterface.aidl
package com.mingyang.webview;

// Declare any non-default types here with import statements
// 跨进程通讯
interface IWebViewPToMainPInterface {
    void handleWebCommend(String commandName,String jsonParams);
}