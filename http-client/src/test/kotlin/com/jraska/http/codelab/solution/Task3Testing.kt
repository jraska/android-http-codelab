package com.jraska.http.codelab.solution

import com.jraska.http.codelab.HttpModuleSolution
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
    httpBinClient = HttpModuleSolution.httpBinClient(mockWebServer.url("/"))
  }

  @Test
  fun simpleGet() {
    mockWebServer.enqueue("response/get.json")

    val requestInfo = httpBinClient.get().body()

    assertThat(requestInfo.origin).isEqualTo("92.178.82.249")
    assertThat(requestInfo.headers["X-Amzn-Trace-Id"]).isEqualTo("Root=1-63795506-1c21ed05383a0466336b0e2b")
  }

  @Test
  fun getWithParameters() {
    mockWebServer.enqueue("response/get_with_parameters.json")

    val result = httpBinClient.get(
      parameters = mapOf(
        "hello" to "world",
        "beer" to "tastes great",
      )
    ).body()

    assertThat(result.args["hello"]).isEqualTo("world")
    assertThat(result.args["beer"]).isEqualTo("tastes great")

    val urlString = mockWebServer.takeRequest().requestUrl.toString()
    assertThat(urlString).contains("hello=world")
    assertThat(urlString).contains("beer=tastes%20great")
  }

  @Test
  fun getUserDetail() {
    mockWebServer.enqueue("response/swankjesse.json")

    val gitHubClient = HttpModuleSolution.gitHubClient(mockWebServer.url("/"))

    val userDetail = runBlocking {
      gitHubClient.getUserDetail("swankjesse")
    }

    assertThat(userDetail.login).isEqualTo("swankjesse")
  }

  @Test
  fun getUserDetailFromRepository() {
    mockWebServer.enqueue("response/swankjesse.json")

    val gitHubUsersRepository = GitHubUsersRepository(
      HttpModuleSolution.gitHubClient(mockWebServer.url("/"))
    )

    val user = runBlocking { gitHubUsersRepository.getUser("swankjesse") }

    assertThat(user).usingRecursiveComparison().isEqualTo(UserFixtures.swankjesse())
  }

  @Test
  fun getUserWithRepos() {
    mockWebServer.onUrlReturn(".*/users/swankjesse".toRegex(), "response/swankjesse.json")
    mockWebServer.onUrlPartReturn("users/swankjesse/repos", "response/swankjesse_repos.json")

    val gitHubUsersRepository = GitHubUsersRepository(
      HttpModuleSolution.gitHubClient(mockWebServer.url("/"))
    )

    val userDetail = runBlocking { gitHubUsersRepository.getUserWithRepos("swankjesse") }

    assertThat(userDetail).usingRecursiveComparison().isEqualTo(UserFixtures.swankjesseWith3Repos())
  }
}
