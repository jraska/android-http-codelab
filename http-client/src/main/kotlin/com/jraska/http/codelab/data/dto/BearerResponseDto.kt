package com.jraska.http.codelab.data.dto

import com.google.gson.annotations.SerializedName

class BearerResponseDto(
  @SerializedName("authenticated")
  var authenticated: Boolean,

  @SerializedName("token")
  var token: String
)
