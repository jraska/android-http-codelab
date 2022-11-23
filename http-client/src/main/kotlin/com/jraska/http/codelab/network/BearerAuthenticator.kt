package com.jraska.http.codelab.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class BearerAuthenticator(private val token: String) : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    return response
      .request
      .newBuilder()
      .addHeader("Authorization", "Bearer $token")
      .build()
  }
}
