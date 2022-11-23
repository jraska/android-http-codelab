package com.jraska.http.codelab.test

import retrofit2.Call
import retrofit2.Response
import kotlin.math.min
import kotlin.math.pow
import kotlin.random.Random

fun <T> Call<T>.retryLinear(count: Int, delayMillis: Long = 2_000): Response<T> {
  repeat(count) {
    val result = clone().execute()
    if (result.isSuccessful) {
      return result
    }

    Thread.sleep(delayMillis)
  }

  throw IllegalStateException("Call failed after $count tries")
}

private const val POWER_BASE = 1.5

fun <T> Call<T>.exponentialBackoff(
  baseDelayMs: Int = 200,
  maxDelayMs: Long = 10_000,
  maxAttempts: Int = 10
): Response<T> {
  repeat(maxAttempts) { index ->
    val result = clone().execute()
    if (result.isSuccessful) {
      return result
    }

    val exponentialDelay = POWER_BASE.pow(index.toDouble()) * baseDelayMs + Random.nextInt(baseDelayMs)
    val nextDelay = min(exponentialDelay.toLong(), maxDelayMs)

    println("Exponential backoff #$index: $nextDelay ms")
    Thread.sleep(nextDelay)
  }

  throw IllegalStateException("Call failed after $maxAttempts tries")
}


