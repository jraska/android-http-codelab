package com.jraska.http.codelab

import com.jraska.http.codelab.data.GitHubUsersClient
import com.jraska.http.codelab.data.HttpBinClient
import com.jraska.http.codelab.network.*
import okhttp3.CertificatePinner
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpModule {
  fun httpBinClient(baseUrl: HttpUrl = "https://httpbin.org".toHttpUrl()): HttpBinClient {
    return retrofit(baseUrl).create(HttpBinClient::class.java)
  }

  fun gitHubClient(baseUrl: HttpUrl = "https://api.github.com".toHttpUrl()): GitHubUsersClient {
    return retrofit(baseUrl).create(GitHubUsersClient::class.java)
  }

  private fun retrofit(baseUrl: HttpUrl): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okClient())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  private fun okClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addNetworkInterceptor(loggingInterceptor())
      .addNetworkInterceptor(EtagInjectHeadersInterceptor())
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(3, TimeUnit.SECONDS)
      .addNetworkInterceptor(BlockGitHubHostInterceptor())
      .build()
  }

  private fun loggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { println(it) }.apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  private fun httpBinCertificatePinner() = CertificatePinner.Builder()
    .add("httpbin.org", "sha256/9uiEUVtJyf/LAciFcfkWlBcrPor9uZGmteP3x9sBGiQ=")
    .build()
}
