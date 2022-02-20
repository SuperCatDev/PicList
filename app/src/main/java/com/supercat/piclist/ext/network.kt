package com.supercat.piclist.ext

import kotlinx.coroutines.*

suspend fun <T : Any> retryFor(
    millis: Long,
    dispatcher: CoroutineDispatcher,
    body: suspend () -> T?
): T = coroutineScope {
    withContext(dispatcher) {
        var result: T? = null

        while (isActive && result == null) {
            result = body.invoke()

            if (result == null) {
                delay(millis)
            }
        }

        result!!
    }
}