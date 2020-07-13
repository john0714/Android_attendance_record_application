package com.example.kotlin_work

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() { // このViewModelを破棄すると、処理がキャンセルされる仕組み
    val userLiveData = MutableLiveData<User>()

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