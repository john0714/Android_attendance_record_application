package com.example.kotlin_work

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(): ViewModel() {
    val timeLiveData = MutableLiveData<String>()

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
}