package com.jraska.http.codelab.domain

import com.jraska.http.codelab.data.GitHubUsersClient
import com.jraska.http.codelab.data.dto.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GitHubUsersRepository(
  private val gitHubUsersApi: GitHubUsersClient
) {

  suspend fun getUser(login: String): User {
    val userDetailDto = gitHubUsersApi.getUserDetail(login)

    return UserMapper.mapUser(userDetailDto)
  }

  suspend fun getUserWithRepos(login: String, reposInSection: Int = 3): UserDetail {
    return withContext(Dispatchers.IO) { // Dispatcher.IO would be injected in real app
        val asyncDetail = async { gitHubUsersApi.getUserDetail(login) }
        val asyncRepo = async { gitHubUsersApi.getRepos(login) }

        awaitAll(asyncDetail, asyncRepo)

        UserMapper.mapUserDetail(
            asyncDetail.await(),
            asyncRepo.await(),
            reposInSection
        )
    }
  }
}
