package com.example.kotlin_work

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginViewModel(
    private val client: OkHttpClient
): ViewModel() { // このViewModelを破棄すると、処理がキャンセルされる仕組み
    val userLiveData = MutableLiveData<User>()
    val responseBody = MutableLiveData<String>()
    var userToken: String = ""

    // responseTest
    private fun fetch(requestBody: RequestBody) {
        viewModelScope.launch {
            val request = Request.Builder().apply {
                post(requestBody)
                url("https://us-central1-kotlinproject-33677.cloudfunctions.net/login")
            }.build()

            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            if (response.code != 200) {
                return@launch
            }

            val body = withContext(Dispatchers.IO) {
                response.body?.string() ?: ""
            }

            responseBody.value = body
        }
    }

    fun login(email: String, password: String) { // co-routine
        viewModelScope.launch {
            val response = getToken(email, password)

            if (response.code != 200) {
                return@launch
            }

            val token = JSONObject(response.body?.string() ?: "")
            userToken = token.getString("access_token")
            val user = getUser(userToken)

            // ここでUIスレッドが実行される
            // setValue()を使っても大丈夫
            userLiveData.value = user
        }
    }

    // firebaseからトークン取得
    private suspend fun getToken(email: String, password: String) = withContext(Dispatchers.IO) {
        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)
        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder().apply {
            post(body)
            url("https://us-central1-kotlinproject-33677.cloudfunctions.net/login")
        }.build()

        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        response
    }
    
    // token에서 유저정보 취득(원래는 DB에 연결해서 취득해야함)
    private suspend fun getUser(token: String) = withContext(Dispatchers.IO) {
        // 元ならここでDBと繋いてユーザ情報取得が、今回は仮のユーザーを使う
        User(id = "user0001", name = "Sample User")
    }
}