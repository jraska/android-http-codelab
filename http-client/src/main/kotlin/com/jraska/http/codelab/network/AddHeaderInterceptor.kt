package com.jraska.http.codelab.network

import okhttp3.Interceptor
import okhttp3.Response

class AddHeaderInterceptor(
  private val name: String,
  private val value: String,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val newRequest = chain.request()
      .newBuilder()
      .addHeader(name, value)
      .build()

    return chain.proceed(newRequest)
  }


}
