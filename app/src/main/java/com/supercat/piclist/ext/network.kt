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

inline fun <T, R> T.tryOrNull(block: (T) -> R): R? {
    if (this == null) return null

    return try {
        block(this)
    } catch (e: Exception) {
        null
    }
}