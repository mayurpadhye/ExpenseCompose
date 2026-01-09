package com.zaplogic.expensetracker.data.repositoryImpl

import android.content.SharedPreferences
import com.zaplogic.expensetracker.domain.shared_prefernce.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferenceManager {
    private val editor = sharedPreferences.edit()

    companion object Companion {
        private const val PREF_NAME = "ExpenseTracker"
        private const val IS_USER_LOGGED_IN = "isUserLoggedIn"
        private const val USER_EMAIL = "user_email"
        private const val USER_ID = "user_id"
        private const val ACCESS_TOKEN = "access_token"

    }



    override fun isUserLoggedIn() : Boolean{
        return  sharedPreferences.getBoolean(IS_USER_LOGGED_IN,false)

    }

    override fun setIsUserLoggedIn(isUserLoggedIn : Boolean){
        editor.putBoolean(IS_USER_LOGGED_IN,isUserLoggedIn).apply()
    }

    override fun saveUserEmail(email : String?) {
        editor.putString(USER_EMAIL,email).apply()
    }

    override  fun saveUserId(id : Int?) {
        editor.putInt(USER_ID,id?:-1).apply()
    }

    override fun getUserId(): Int? {
       return sharedPreferences.getInt(USER_ID,-1)
    }

    override fun saveAccessToken(accessToken : String?) {
        editor.putString(ACCESS_TOKEN,accessToken).apply()
    }

    override fun getAccessToken() : String? {
        return sharedPreferences.getString(ACCESS_TOKEN,null)
    }

    override fun getUserEmail() : String? {
        return sharedPreferences.getString(USER_EMAIL,null)
    }

    override fun clear(){
        editor.clear().apply()
    }


}