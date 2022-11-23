package com.jraska.http.codelab.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GitHubUserDto {

  @SerializedName("login")
  @Expose
  var login: String? = null

  @SerializedName("avatar_url")
  @Expose
  var avatarUrl: String? = null

  @SerializedName("html_url")
  @Expose
  var htmlUrl: String? = null

  @Expose
  @SerializedName("site_admin")
  var siteAdmin: Boolean? = null
}
