package com.example.mobprogtk4.Login

data class SignInResult (
    val data: UserData?,
    val errorMessage: String?
) {
}

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?,

)