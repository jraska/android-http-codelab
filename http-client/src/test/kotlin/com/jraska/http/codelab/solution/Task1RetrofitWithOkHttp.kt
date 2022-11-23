package com.jraska.http.codelab.solution

import com.jraska.http.codelab.HttpModuleSolution
import com.jraska.http.codelab.network.body
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Task1RetrofitWithOkHttp {
  private val httpBinClient = HttpModuleSolution.httpBinClient()

  @Test
  fun simpleGet() {
    val requestInfo = httpBinClient.get().body()

    assertThat(requestInfo.headers).isNotEmpty
  }

  @Test
  fun getWithParameters() {
    val result = httpBinClient.get(
      parameters = mapOf(
        "hello" to "world",
        "beer" to "tastes great",
      )
    ).body()

    assertThat(result.args["hello"]).isEqualTo("world")
    assertThat(result.args["beer"]).isEqualTo("tastes great")
  }

  @Test
  fun dripTimeouts() {
    val result = httpBinClient.drip(3, 2).body()

    assertThat(result).isNotNull
  }

  @Test
  fun authentication() {
    val bearerResponse = httpBinClient.bearer().body()

    assertThat(bearerResponse.authenticated).isEqualTo(true)
  }
}
