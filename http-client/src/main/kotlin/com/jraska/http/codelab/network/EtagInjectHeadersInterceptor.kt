package com.jraska.http.codelab.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

// this is a hack for the purpose of the Codelab - real server will properly set the Cache-Control headers
class EtagInjectHeadersInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val response = chain.proceed(chain.request())

    return if (isEtagRequest(response.request)) {
      response.newBuilder().addHeader("cache-control", "max-age=3").build()
    } else {
      response
    }
  }

  private fun isEtagRequest(request: Request): Boolean {
    val pathSegments = request.url.pathSegments
    return pathSegments.isNotEmpty() && pathSegments[0] == "etag"
  }
}
