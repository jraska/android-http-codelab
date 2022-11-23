package com.jraska.http.codelab.test

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class MapDispatcher : Dispatcher() {
  private val requestDispatchers = mutableSetOf<NetworkMockEntry>()

  fun onMatchingReturn(matcher: Matcher<RecordedRequest>, mockResponse: MockResponse) {
    requestDispatchers.add(
      NetworkMockEntry(matcher) { mockResponse })
  }

  override fun dispatch(request: RecordedRequest): MockResponse {
    val matchingEntries = requestDispatchers.filter { it.matcher.matches(request) }
    when (matchingEntries.size) {
      1 -> return matchingEntries.single().responseFactory(request)

      0 -> throw IllegalStateException("No mocked response found for request: $request")
      else -> throw IllegalStateException("Multiple matched mocked responses for request: $request,\nmatches: $matchingEntries")
    }
  }

  data class NetworkMockEntry(val matcher: Matcher<RecordedRequest>, val responseFactory: (RecordedRequest) -> MockResponse)
}

class UrlContainsMatcher(private val urlPart: String) : TypeSafeMatcher<RecordedRequest>() {
  override fun describeTo(description: Description) {
    description.appendText("url contains: $urlPart")
  }

  override fun matchesSafely(request: RecordedRequest): Boolean {
    return request.requestUrl.toString().contains(urlPart)
  }
}

class UrlRegexMatcher(private val urlRegex: Regex) : TypeSafeMatcher<RecordedRequest>() {
  override fun describeTo(description: Description) {
    description.appendText("url matches: $urlRegex")
  }

  override fun matchesSafely(request: RecordedRequest): Boolean {
    return urlRegex.matches(request.requestUrl.toString())
  }
}
