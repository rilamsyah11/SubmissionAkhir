package com.rizki.submissionakhir.networking

import com.rizki.submissionakhir.response.GithubUser
import com.rizki.submissionakhir.response.ResponseGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun cariUser(
        @Query("q") login: String
    ): Call<ResponseGithub>

    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<GithubUser>

    @GET("users/{login}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("login") login: String
    ): Call<ArrayList<GithubUser>>

    @GET("users/{login}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("login") login: String
    ): Call<ArrayList<GithubUser>>


}
