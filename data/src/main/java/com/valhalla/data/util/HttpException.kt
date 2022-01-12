package com.valhalla.data.util

import java.lang.RuntimeException

class HttpException(
    val code: Int = 500,
    val errorCode: String = "",
    override val message: String = ""
) : RuntimeException(message)
