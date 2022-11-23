package com.jraska.http.codelab

import com.jraska.http.codelab.network.body
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Task2Caching {
  private val httpBinClient = HttpModule.httpBinClient()

  /**
   * 1. Setup a response cache in directory "httpCodelabCache", set size to 2MB
   * Try to change the cacheMaxAge together with Thread sleep to see the behaviour
   *
   * @see okhttp3.Cache
   * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control
   * @see https://square.github.io/okhttp/features/caching/
   */
  @Test
  fun cachesResponse() {
    val cacheMaxAge = 5
    val result = httpBinClient.cacheControl(cacheMaxAge).body()
    val traceId = result.headers["X-Amzn-Trace-Id"]!!

//    Thread.sleep(2_100)

    val secondTraceId = httpBinClient.cacheControl(cacheMaxAge).body().headers["X-Amzn-Trace-Id"]
    assertThat(secondTraceId).isEqualTo(traceId)
  }

  /**
   * 2. Change the second request ETag to match the first request etag - get 304 status code on the second request
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag
   * @see okhttp3.Cache
   * @see https://square.github.io/okhttp/features/caching/
   */
  @Test
  fun eTag() {
    val theCachedEtag = "77f2e4"
    val firstResult = httpBinClient.eTag(theCachedEtag).body()

    Thread.sleep(3_100)

    val eTagResult = httpBinClient.eTag("otherEtag").body()
    assertThat(eTagResult).usingRecursiveComparison().isEqualTo(firstResult)
  }
}
