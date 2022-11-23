package com.jraska.http.codelab.data.dto

import com.jraska.http.codelab.domain.RepoHeader
import com.jraska.http.codelab.domain.User
import com.jraska.http.codelab.domain.UserDetail
import com.jraska.http.codelab.domain.UserStats
import java.time.Instant

object UserMapper {

  fun mapUser(gitHubUser: GitHubUserDetailDto): User {
    val isAdmin = gitHubUser.siteAdmin ?: false
    return User(gitHubUser.login!!, gitHubUser.avatarUrl!!, isAdmin)
  }

  fun mapUserDetail(
    gitHubUserDetail: GitHubUserDetailDto,
    gitHubRepos: List<GitHubUserRepoDto>,
    reposToDisplay: Int
  ): UserDetail {
    val joined = Instant.parse(gitHubUserDetail.createdAt)

    val stats = UserStats(
      gitHubUserDetail.followers!!, gitHubUserDetail.following!!,
      gitHubUserDetail.publicRepos!!, joined
    )

    val sortedRepos = gitHubRepos.sortedWith(compareByDescending { it.stargazersCount })

    val usersRepos = ArrayList<RepoHeader>()
    val contributedRepos = ArrayList<RepoHeader>()

    for (gitHubRepo in sortedRepos) {
      if (usersRepos.size < reposToDisplay && gitHubUserDetail.login == gitHubRepo.owner!!.login) {
        val repo = map(gitHubRepo)
        usersRepos.add(repo)
      } else if (contributedRepos.size < reposToDisplay) {
        val repo = map(gitHubRepo)
        contributedRepos.add(repo)
      }
    }

    val user = mapUser(gitHubUserDetail)
    return UserDetail(user, stats, usersRepos, contributedRepos)
  }

  private fun map(gitHubRepo: GitHubUserRepoDto): RepoHeader {
    return RepoHeader(
      gitHubRepo.owner!!.login!!,
      gitHubRepo.name!!, gitHubRepo.description ?: "",
      gitHubRepo.stargazersCount!!, gitHubRepo.forks!!
    )
  }
}
