package com.example.kotlin_work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class VacationInformationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vacation_information, container, false) // view 오브젝트를 생성함(fragment_main)

        // 페이지 이동 - 근무 입력
        val insertButton= view.findViewById<Button>(R.id.button_goToMain) // 그 안에서 버튼을 찾음
        insertButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_vacationInformationFragment_to_mainFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 근무 수정
        val modifyButton= view.findViewById<Button>(R.id.button_goToModify) // 그 안에서 버튼을 찾음
        modifyButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_vacationInformationFragment_to_modifyFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 근무 정보
        val workInformationButton = view.findViewById<Button>(R.id.button_goToWorkInformation) // 그 안에서 버튼을 찾음
        workInformationButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_vacationInformationFragment_to_workInformationFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 페이지 이동 - 로그인 화면(로그아웃)
        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            findNavController().navigate(R.id.action_vacationInformationFragment_to_loginFragment)
        }
    }
}