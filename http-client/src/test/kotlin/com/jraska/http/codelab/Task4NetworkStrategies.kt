package com.jraska.http.codelab

import com.jraska.http.codelab.network.body
import com.jraska.http.codelab.test.exponentialBackoff
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.HttpException

class Task4NetworkStrategies {
  private val httpBinClient = HttpModule.httpBinClient()

  /**
   * 1a. The get request is flaky. Retry the request in a linear way to get 200 status code.
   * If you prefer Coroutines, follow with exercise 1b below.
   *
   * @see com.jraska.http.codelab.data.HttpBinClient.flakyGet
   * @see com.jraska.http.codelab.test.retryLinear
   */
  @Test
  fun linearRetryCall() {
    val response = httpBinClient.flakyGet().execute()

    assertThat(response.isSuccessful).isTrue
  }

  /**
   * 1b. The get request is flaky. Retry the request in a linear way to get 200 status code.
   * If you prefer Call, follow with exercise 1a above.
   *
   * @see getLinearRetries
   * @see com.jraska.http.codelab.data.HttpBinClient.flakyGetCoroutine
   */
  @Test
  fun linearRetryCoroutine() {
    val result = runBlocking { httpBinClient.flakyGetCoroutine() }

    assertThat(result).isNotNull
  }

  /**
   * 2. Implement randomized exponential backoff.
   *
   * @see https://cloud.google.com/iot/docs/how-tos/exponential-backoff#example_algorithm
   *
   * @see com.jraska.http.codelab.test.exponentialBackoff
   * @see com.jraska.http.codelab.data.HttpBinClient.flakyGet
   * @see com.jraska.http.codelab.data.HttpBinClient.flakyGetCoroutine
   */
  @Test
  fun exponentialBackoff() {
    val response = httpBinClient.flakyGet().execute()

    assertThat(response.isSuccessful).isTrue
  }

  private suspend fun getLinearRetries(): ResponseBody {
    val count = 10
    val delayMillis = 300L

    repeat(count) {
      try {
        return httpBinClient.flakyGetCoroutine()
      } catch (httpException: HttpException) {
        // retrying
      }

      delay(delayMillis)
    }

    throw IllegalStateException("Call failed after $count tries")
  }
}
