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
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(
    private val client: OkHttpClient
): ViewModel() {
    val timeLiveData = MutableLiveData<String>()
    val responseBody = MutableLiveData<String>()

    // 로그아웃
    fun logout(email: String, password: String) {
        // 로그아웃하고 뒤로가기하면 다시 로그인 되버리니까, App에서 그걸 막는 세션을 지정하는걸 알아봐야겠네
    }

    // 시간 세팅(LiveData)
    fun setNowTime() {
        // set timeZone
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.JAPAN)
        val time: String? = ZonedDateTime.now(ZoneId.of("Asia/Tokyo")).format(formatter)

        timeLiveData.value = time
    }

    // 출근 / 퇴근
    fun doStamping(type: String, userToken: String) {
        viewModelScope.launch {
            val response = stamping(type, userToken)
            println(response)

            if (response.code != 200) {
                return@launch
            }
        }
    }

    // 입력
    private suspend fun stamping(type: String, token: String) = withContext(Dispatchers.IO) {
        val json = JSONObject()
        json.put("type", type)
        json.put("timestamp", timeLiveData.value)
        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        println(token)

        // 이거 쓸려면 token이 필요한데, 로그인 했을때 얻는 토큰을 어떻게 여기로 가져와서 사용하지?
        // SharedPreference를 사용해서 toeken을 가져와야함
        val request = Request.Builder().apply {
            post(body)
            url("https://us-central1-kotlinproject-33677.cloudfunctions.net/add_timestamp")
        }.build()

        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        response
    }
}