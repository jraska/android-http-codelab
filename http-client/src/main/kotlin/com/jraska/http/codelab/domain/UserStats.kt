package com.jraska.http.codelab.domain

import java.time.Instant

class UserStats(
  val followers: Int,
  val following: Int,
  val publicRepos: Int,
  val joined: Instant
)
