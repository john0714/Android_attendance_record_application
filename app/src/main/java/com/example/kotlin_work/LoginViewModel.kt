package com.example.kotlin_work

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class LoginViewModel(
    private val client: OkHttpClient
): ViewModel() { // このViewModelを破棄すると、処理がキャンセルされる仕組み
    val userLiveData = MutableLiveData<User>()
    val responseBody = MutableLiveData<String>()

    fun fetch() {
        viewModelScope.launch {
            val request = Request.Builder().apply {
                get()
                url("https://httpbin.org/get")
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
            val token = getToken(email, password)
            val user = getUser(token)

            // ここでUIスレッドが実行される
            // setValue()を使っても大丈夫
            userLiveData.value = user
        }
    }

    private suspend fun getToken(email: String, password: String) = withContext(Dispatchers.IO) {
        println("ログイン処理開始")
        delay(2000)
        "access_token0000"
    }

    private suspend fun getUser(token: String) = withContext(Dispatchers.IO) {
        println("ユーザー情報取得処理の開始")
        delay(1000)
        User(id = "user0001", name = "Sample User")
    }
}