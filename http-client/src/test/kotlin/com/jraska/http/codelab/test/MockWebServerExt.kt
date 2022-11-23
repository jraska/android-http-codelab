package com.jraska.http.codelab.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.File

fun MockWebServer.enqueue(path: String) {
  this.enqueue(MockResponse().setBody(json(path)))
}

fun json(path: String): String {
  val uri = MapDispatcher::class.java.classLoader.getResource(path)
  val file = File(uri?.path!!)
  return String(file.readBytes())
}

fun jsonResource(path: String): MockResponse {
  return MockResponse().setBody(json(path))
}

fun MockWebServer.onUrlPartReturn(urlPart: String, jsonPath: String) =
  onUrlPartReturn(urlPart, jsonResource(jsonPath))

fun MockWebServer.onUrlPartReturn(urlPart: String, mockResponse: MockResponse) {
  ensureMapDispatcher().onMatchingReturn(UrlContainsMatcher(urlPart), mockResponse)
}

fun MockWebServer.onUrlReturn(urlRegex: Regex, jsonPath: String) =
  onUrlReturn(urlRegex, jsonResource(jsonPath))

fun MockWebServer.onUrlReturn(urlRegex: Regex, mockResponse: MockResponse) {
  ensureMapDispatcher().onMatchingReturn(UrlRegexMatcher(urlRegex), mockResponse)
}

private fun MockWebServer.ensureMapDispatcher(): MapDispatcher {
  if (dispatcher !is MapDispatcher) {
    dispatcher = MapDispatcher()
  }

  return dispatcher as MapDispatcher
}
