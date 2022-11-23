package com.jraska.http.codelab.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GitHubUserRepoDto {
  @SerializedName("id")
  @Expose
  var id: Int? = null

  @SerializedName("name")
  @Expose
  var name: String? = null

  @SerializedName("owner")
  @Expose
  var owner: GitHubUserDto? = null

  @SerializedName("html_url")
  @Expose
  var description: String? = null

  @SerializedName("stargazers_count")
  @Expose
  var stargazersCount: Int? = null

  @SerializedName("forks")
  @Expose
  var forks: Int? = null
}
