package com.mingyang.base

import java.lang.Exception
import java.util.*

object BaseServiceLoader {

    fun <S> load(clazz: Class<S>): S? {
        return try {
            ServiceLoader.load(clazz).iterator().next()
        } catch (e: Exception) {
            null
        }
    }
}