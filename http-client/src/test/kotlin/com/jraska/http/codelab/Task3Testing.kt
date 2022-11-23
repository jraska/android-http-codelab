package com.jraska.http.codelab

import com.jraska.http.codelab.domain.GitHubUsersRepository
import com.jraska.http.codelab.fixtures.UserFixtures
import com.jraska.http.codelab.data.HttpBinClient
import com.jraska.http.codelab.network.body
import com.jraska.http.codelab.test.enqueue
import com.jraska.http.codelab.test.onUrlPartReturn
import com.jraska.http.codelab.test.onUrlReturn
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class Task3Testing {
  @get:Rule
  val mockWebServer = MockWebServer()

  private lateinit var httpBinClient: HttpBinClient

  @Before
  fun setUp() {
    httpBinClient = HttpModule.httpBinClient(mockWebServer.url("/"))
  }

  /**
   * 1. The httpbin.org.get request times out as it is not faked - enqueue request from "response/get.json"
   *
   * @see com.jraska.http.codelab.test.enqueue
   */
  @Test
  fun simpleGet() {

    val requestInfo = httpBinClient.get().body()

    assertThat(requestInfo.origin).isEqualTo("92.178.82.249")
    assertThat(requestInfo.headers["X-Amzn-Trace-Id"]).isEqualTo("Root=1-63795506-1c21ed05383a0466336b0e2b")
  }

  /**
   * 2. Use the "response/get_with_parameters.json" to fake the web request
   * Send url parameters to pass the assertions
   *
   * @see com.jraska.http.codelab.test.enqueue("path")
   */
  @Test
  fun getWithParameters() {

    val result = httpBinClient.get().body()

    assertThat(result.args["hello"]).isEqualTo("world")
    assertThat(result.args["beer"]).isEqualTo("tastes great")

    val urlString = mockWebServer.takeRequest().requestUrl.toString()
    assertThat(urlString).contains("hello=world")
    assertThat(urlString).contains("beer=tastes%20great")
  }

  /**
   * 3. This test is trying to do real requests,change the base url to point to mockWebServer
   *
   * @see okhttp3.mockwebserver.MockWebServer.url
   * @see com.jraska.http.codelab.HttpModule.gitHubClient
   * @see com.jraska.http.codelab.network.BlockGitHubHostInterceptor
   */
  @Test
  fun getUserDetail() {
    mockWebServer.enqueue("response/swankjesse.json")

    val gitHubClient = HttpModule.gitHubClient(/* defaults to https://api.github.com*/)

    val userDetail = runBlocking {
      gitHubClient.getUserDetail("swankjesse")
    }

    assertThat(userDetail.login).isEqualTo("swankjesse")
  }

  /**
   * 4. This test gets User domain object, but is not asserting anythign.
   * Assert all the :user" fields are correctly set as in UserFixtures.swankjesse()
   *
   * @see com.jraska.http.codelab.domain.User
   * @see com.jraska.http.codelab.fixtures.UserFixtures.swankjesse
   * @see org.assertj.core.api.AbstractObjectAssert.usingRecursiveComparison
   */
  @Test
  fun getUserDetailFromRepository() {
    mockWebServer.enqueue("response/swankjesse.json")

    val gitHubUsersRepository = GitHubUsersRepository(
      HttpModule.gitHubClient(mockWebServer.url("/"))
    )

    val user = runBlocking { gitHubUsersRepository.getUser("swankjesse") }
  }

  /**
   * 5. This test does 2 network requests in parallel through repository enqueueing requests might not be enough.
   * Use your own Dispatcher to match requests by url.
   *
   * @see okhttp3.mockwebserver.MockWebServer.dispatcher
   * @see com.jraska.http.codelab.test.onUrlReturn
   * @see com.jraska.http.codelab.test.onUrlPartReturn\
   */
  @Test
  fun getUserWithRepos() {

    val gitHubUsersRepository = GitHubUsersRepository(
      HttpModule.gitHubClient(mockWebServer.url("/"))
    )

    val userDetail = runBlocking { gitHubUsersRepository.getUserWithRepos("swankjesse") }

    assertThat(userDetail).usingRecursiveComparison().isEqualTo(UserFixtures.swankjesseWith3Repos())
  }
}
