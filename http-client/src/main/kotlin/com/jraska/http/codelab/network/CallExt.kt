package com.jraska.http.codelab.network

import retrofit2.Call

fun <T> Call<T>.body(): T {
  val response = execute()
  if (response.isSuccessful) {
    return response.body()!!
  } else {
    throw retrofit2.HttpException(response)
  }
}
