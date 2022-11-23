package com.jraska.http.codelab.fixtures

import com.jraska.http.codelab.domain.RepoHeader
import com.jraska.http.codelab.domain.User
import com.jraska.http.codelab.domain.UserDetail
import com.jraska.http.codelab.domain.UserStats
import java.time.Instant

object UserFixtures {
  fun swankjesse(): User {
    return User("swankjesse", "https://avatars.githubusercontent.com/u/133019?v=4", false)
  }

  fun swankjesseWith3Repos(): UserDetail {
    return UserDetail(
      swankjesse(),
      UserStats(5942, 2, 24, Instant.parse("2009-09-30T02:51:25Z")),
      popularRepos = listOf(
        RepoHeader("swankjesse", "encoding", "https://github.com/swankjesse/encoding", 33, 4),
        RepoHeader("swankjesse", "dex", "https://github.com/swankjesse/dex", 21, 5),
        RepoHeader("swankjesse", "concurrency", "https://github.com/swankjesse/concurrency", 11, 1),
      ),
      contributedRepos = listOf(
        RepoHeader("google", "gson", "https://github.com/google/gson", 21619, 4144),
        RepoHeader(
          "JakeWharton",
          "DiskLruCache",
          "https://github.com/JakeWharton/DiskLruCache",
          5718,
          1195
        ),
        RepoHeader("square", "kotlinpoet", "https://github.com/square/kotlinpoet", 3355, 256)
      )
    )
  }
}
