package com.zaplogic.expensetracker.domain.shared_prefernce

interface PreferenceManager {

    fun isUserLoggedIn() : Boolean
    fun setIsUserLoggedIn(isUserLoggedIn : Boolean)
    fun saveUserEmail(email : String?)
    fun saveUserId(id : Int?)
    fun getUserId(): Int?
    fun saveAccessToken(accessToken : String?)
    fun getAccessToken() : String?
    fun getUserEmail() : String?
    fun clear()
}