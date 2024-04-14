package com.andreich.moviesearcher.presentation

import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
inline fun <T, R> T.runCatchingCancellable(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        if (e is CancellationException) {
            throw e
        }
        Result.failure(e)
    }
}