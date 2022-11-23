package com.jraska.http.codelab.domain

import com.jraska.http.codelab.domain.RepoHeader
import com.jraska.http.codelab.domain.User

class UserDetail(
  val user: User,
  val basicStats: UserStats?,
  val popularRepos: List<RepoHeader>,
  val contributedRepos: List<RepoHeader>
)
