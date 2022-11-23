# Android HTTP Codelab
- Codelab to demonstrate networking on Android and JVM

# What will you learn?
- [OkHttp](https://square.github.io/okhttp/) and [Retrofit](https://square.github.io/retrofit/) libraries
  - Basics of HTTP - headers, path, queries, timeouts
  - Modifying requests by interceptors
  - Authentication, SSL, Certificate Pinning
  - OkHttp internals and philosophy
- Caching
  - Cache-Control Headers and their use cases
  - Etag usage
- Testing Networking code
  - Using MockWebServer
  - Testing whole repositories
  - Complex networking scenarios with parallel requests
- Client network strategies
  - Simple retries
  - Exponential backoff
  - Network throttling

# How to use the Codelab
- You can find the unit tests within the [`codelab`](https://github.com/jraska/android-http-codelab/tree/master/http-client/src/test/kotlin/com/jraska/http/codelab) directory in the format `Test{Number}{Topic}`
- Each test has comments with instructions and references to guide to the solution.
- There is a [solution](https://github.com/jraska/android-http-codelab/tree/master/http-client/src/test/kotlin/com/jraska/http/codelab/solution) package having identical `Test{Number}{Topic}` name with working code.
- Other key classes to look at are: `HttpModule`, `HttpBinClient`, `GitHubUsersClient`
- Most of the methods use the Retrofit `Call` and we move to Coroutines `suspend` functions through the codelab.

# References
- TBD
