package com.jraska.http.codelab

import com.jraska.http.codelab.network.body
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Task1RetrofitWithOkHttp {
  private val httpBinClient = HttpModule.httpBinClient()

  /**
   * 1. Perform a simple GET method to httpbin.org/get
   *
   * @see com.jraska.http.codelab.data.HttpBinClient.get
   * @see com.jraska.http.codelab.network.body
   */
  @Test
  fun simpleGet() {

//    assertThat(requestInfo.headers).isNotEmpty
  }

  /**
   *  2. Send multiple query parameters to GET httpbin.org/get
   *  Add "User-Agent: Http Codelab" header to all requests
   *
   *  @see com.jraska.http.codelab.network.AddHeaderInterceptor
   */
  @Test
  fun getWithParameters() {


//    assertThat(requestInfo.args["hello"]).isEqualTo("world")
//    assertThat(requestInfo.args["beer"]).isEqualTo("tastes great")
  }

  /**
   *  3. The drip request is timing out, change timeout or drip delay to avoid the timeout
   *  Adjust timeout only for one type of requests - /drip
   *  @see com.jraska.http.codelab.network.DripReadTimeoutInterceptor
   */
  @Test
  fun dripTimeouts() {
    val result = httpBinClient.drip(3, 4).body()

    assertThat(result).isNotNull
  }

  /**
   * 4. Authenticate bearer method when receiving 401 - Unauthorized
   * Implement certificate pinning to httpbin.org
   *
   * @see okhttp3.Authenticator
   * @see com.jraska.http.codelab.network.BearerAuthenticator
   * @see https://httpbin.org/#/Auth/get_bearer
   *
   * @see okhttp3.CertificatePinner
   * @see com.jraska.http.codelab.HttpModule.httpBinCertificatePinner
   * @see https://developer.android.com/training/articles/security-config#CertificatePinning
   */
  @Test
  fun authentication() {
    val bearerResponse = httpBinClient.bearer().body()

    assertThat(bearerResponse.authenticated).isEqualTo(true)
  }
}
