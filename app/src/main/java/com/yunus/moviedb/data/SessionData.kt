package com.yunus.moviedb.data

import com.google.gson.annotations.SerializedName

data class CreateSessionLoginRequest(
    @SerializedName("username") var userName: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("request_token") var requestToken: String = ""
)

data class CreateRequestTokenResponse(
    @SerializedName("success") var isSuccess: Boolean?,
    @SerializedName("request_token") var requestToken: String?
) : Session()


open class Session {
    @SerializedName("expires_at") var expireAt: String? = ""
    @SerializedName("session_id") var sessionId: String? = ""
}