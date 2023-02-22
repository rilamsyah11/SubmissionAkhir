package com.rizki.submissionakhir.response

import com.google.gson.annotations.SerializedName

data class ResponseGithub(
    @field:SerializedName("items")
    val listUserGithub : ArrayList<GithubUser>

)

data class GithubUser(
    @field:SerializedName("id")
    val Id: Int?,

    @field:SerializedName("login")
    val Username : String?,

    @field:SerializedName("name")
    val Fullname : String?,

    @field:SerializedName("company")
    val Company : String?,

    @field:SerializedName("location")
    val Location : String?,

    @field:SerializedName("avatar_url")
    val Avatar : String?,

    @field:SerializedName("public_repos")
    val Repository : String?,
)
