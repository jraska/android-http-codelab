package com.jraska.http.codelab.network

import okhttp3.Interceptor
import okhttp3.Response

class BlockGitHubHostInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    if(request.url.host == "api.github.com") {
      throw java.lang.IllegalArgumentException("Restricted real github.com requests in tests!")
    }

    return chain.proceed(request)
  }
}
