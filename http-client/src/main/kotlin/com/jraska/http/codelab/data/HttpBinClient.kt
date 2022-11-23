package com.jraska.http.codelab.data

import com.jraska.http.codelab.data.dto.BearerResponseDto
import com.jraska.http.codelab.data.dto.RequestInfoDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Docs at https://httpbin.org/#/
 */
interface HttpBinClient {
  @GET("/get")
  @Headers("Custom-Header: header-value")
  fun get(
    @QueryMap parameters: Map<String, String> = emptyMap(),
  ): Call<RequestInfoDto>

  @GET("/drip?numbytes=2&code=200")
  fun drip(
    @Query("duration") durationSeconds: Int = 1,
    @Query("delay") delaySeconds: Int = 1
  ): Call<ResponseBody>

  @GET("/bearer")
  fun bearer(): Call<BearerResponseDto>

  @GET("/cache/{cache-max-age}")
  fun cacheControl(@Path("cache-max-age") cacheMaxAge: Int = 60): Call<RequestInfoDto>

  @GET("/etag/{ETag}")
  fun eTag(
    @Path("ETag") eTag: String
  ): Call<RequestInfoDto>

  @GET("/status/200,400,403,404,412,500")
  fun flakyGet(): Call<ResponseBody>

  @GET("/status/200,400,403,404,412,500")
  suspend fun flakyGetCoroutine(): ResponseBody
}
