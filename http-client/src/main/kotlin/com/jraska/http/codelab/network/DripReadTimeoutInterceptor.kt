package com.jraska.http.codelab.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class DripReadTimeoutInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    return if (isDripRequest(request)) {
      increaseReadTimeout(request, chain)
    } else {
      chain.proceed(request)
    }
  }

  private fun isDripRequest(request: Request): Boolean {
    val pathSegments = request.url.pathSegments
    return pathSegments.isNotEmpty() && pathSegments[0] == "drip"
  }

  private fun increaseReadTimeout(
    request: Request,
    chain: Interceptor.Chain
  ): Response {
    val delay = (request.url.queryParameter("delay") ?: "0").toInt()
    val duration = (request.url.queryParameter("duration") ?: "0").toInt()

    return chain.withReadTimeout(delay + duration + 2, TimeUnit.SECONDS).proceed(request)
  }
}
