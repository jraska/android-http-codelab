
# Android HTTP Codelab
- Codelab to demonstrate networking on Android and JVM using OkHttp and Retrofit.

# What will you learn?
- [OkHttp](https://square.github.io/okhttp/) and [Retrofit](https://square.github.io/retrofit/) libraries
  - Basics of [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) - headers, path, queries, timeouts
  - Modifying requests by [interceptors](https://square.github.io/okhttp/features/interceptors/)
  - [Authentication](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-authenticator/), [SSL](https://en.wikipedia.org/wiki/Transport_Layer_Security), [Certificate Pinning](https://owasp.org/www-community/controls/Certificate_and_Public_Key_Pinning)
  - [OkHttp](https://square.github.io/okhttp/) internals and philosophy
- Caching
  - [Cache-Control](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control) Headers and their use cases
  - [Etag](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag) usage
- Testing Networking code
  - Using [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
  - Testing whole repositories
  - Complex networking scenarios with parallel requests
- Client network strategies
  - Simple retries
  - [Exponential backoff](https://cloud.google.com/iot/docs/how-tos/exponential-backoff)
  - [Network throttling](https://sre.google/sre-book/handling-overload/)

# How to use the Codelab
- All code is in the default branch.
- You can find the unit tests within the [`codelab`](https://github.com/jraska/android-http-codelab/tree/master/http-client/src/test/kotlin/com/jraska/http/codelab) directory in the format `Test{Number}{Topic}`
- Each test has comments with instructions and references to guide to the solution.
- There is a [solution](https://github.com/jraska/android-http-codelab/tree/master/http-client/src/test/kotlin/com/jraska/http/codelab/solution) package having identical `Test{Number}{Topic}` name with working code.
- Other key classes to look at are: `HttpModule`, `HttpBinClient`, `GitHubUsersClient`
- Most of the methods use the Retrofit `Call` and we move to Coroutines `suspend` functions through the codelab.

# References
- [HTTP in a Hostile World](https://www.youtube.com/watch?v=tfD2uYjzXFo), [*Slides*](https://speakerdeck.com/swankjesse/http-in-a-hostile-world-droidcon-toronto-2019)
- [Okio powering OkHttp I/O](https://www.youtube.com/watch?v=Du7YXPAV1M8)
- [Android Studio Network Inspector](https://developer.android.com/studio/debug/network-profiler)
- [Cache-Control](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control) & [ETag](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag), [OkHttp Caching](https://square.github.io/okhttp/features/caching/)
- [Gzip](https://en.wikipedia.org/wiki/Gzip), [Content-Length](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length) header 
- [Exponential Backoff](https://cloud.google.com/iot/docs/how-tos/exponential-backoff)
- [OkHttp retries requests by default](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/-builder/retry-on-connection-failure/)
- [URL encoding](https://www.w3schools.com/tags/ref_urlencode.ASP)
- [Client network throttling](https://sre.google/sre-book/handling-overload/)
- [Bearer authentication](https://www.rfc-editor.org/rfc/rfc6750)
- [Android Network Config certificate pinning](https://developer.android.com/training/articles/security-config#CertificatePinning)
