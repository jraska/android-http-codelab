package com.jraska.http.codelab.solution

import com.jraska.http.codelab.HttpModuleSolution
import com.jraska.http.codelab.network.body
import com.jraska.http.codelab.test.exponentialBackoff
import com.jraska.http.codelab.test.retryLinear
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.HttpException

class Task4NetworkStrategies {
  private val httpBinClient = HttpModuleSolution.httpBinClient()

  @Test
  fun linearRetryCall() {
    val response = httpBinClient.flakyGet().retryLinear(10)

    assertThat(response.isSuccessful).isTrue
  }

  @Test
  fun linearRetryCoroutine() {
    val result = runBlocking {
      retriesGet()
    }

    assertThat(result).isNotNull
  }

  @Test
  fun exponentialBackoff() {
    val response = httpBinClient.flakyGet().exponentialBackoff()

    assertThat(response.isSuccessful).isTrue
  }

  private suspend fun retriesGet(): ResponseBody {
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
