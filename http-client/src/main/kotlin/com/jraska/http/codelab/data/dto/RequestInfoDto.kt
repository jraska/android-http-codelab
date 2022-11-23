package com.jraska.http.codelab.data.dto

import com.google.gson.annotations.SerializedName

class RequestInfoDto {
  @SerializedName("args")
  lateinit var args: Map<String, String>

  @SerializedName("headers")
  lateinit var headers: Map<String, String>

  @SerializedName("origin")
  lateinit var origin: String

  @SerializedName("url")
  lateinit var url: String

  @SerializedName("data")
  var data: String? = null

  @SerializedName("json")
  var json: String? = null
}
