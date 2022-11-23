package com.jraska.http.codelab.data

import com.jraska.http.codelab.data.dto.GitHubUserDetailDto
import com.jraska.http.codelab.data.dto.GitHubUserRepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubUsersClient {
  @GET("/users/{login}")
  suspend fun getUserDetail(@Path("login") login: String): GitHubUserDetailDto

  @GET("/users/{login}/repos?type=all")
  suspend fun getRepos(@Path("login") login: String): List<GitHubUserRepoDto>
}
