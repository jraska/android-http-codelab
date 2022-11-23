package com.jraska.http.codelab.solution

import com.jraska.http.codelab.HttpModuleSolution
import com.jraska.http.codelab.network.body
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Task2Caching {
  private val httpBinClient = HttpModuleSolution.httpBinClient()

  @Test
  fun cachesResponse() {
    val cacheMaxAge = 5
    val result = httpBinClient.cacheControl(cacheMaxAge).body()
    val traceId = result.headers["X-Amzn-Trace-Id"]!!

    val secondTraceId = httpBinClient.cacheControl(cacheMaxAge).body().headers["X-Amzn-Trace-Id"]
    assertThat(secondTraceId).isEqualTo(traceId)
  }

  @Test
  fun eTag() {
    val theCachedEtag = "77f2e4"
    httpBinClient.eTag(theCachedEtag).execute()

    Thread.sleep(3_100)

    val eTagResult = httpBinClient.eTag(theCachedEtag).body()
    assertThat(eTagResult).isNotNull
  }
}
