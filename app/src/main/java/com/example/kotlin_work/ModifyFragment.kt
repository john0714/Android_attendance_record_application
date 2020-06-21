package com.example.kotlin_work

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class ModifyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modify, container, false) // view 오브젝트를 생성함(fragment_main)

        // 페이지 이동 - 근무 입력
        val insertButton= view.findViewById<Button>(R.id.button_goToMain) // 그 안에서 버튼을 찾음
        insertButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_modifyFragment_to_mainFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 유급 정보
        val vacationInformationButton = view.findViewById<Button>(R.id.button_goToVacationInformation) // 그 안에서 버튼을 찾음
        vacationInformationButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_modifyFragment_to_vacationInformationFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 근무 정보
        val workInformationButton = view.findViewById<Button>(R.id.button_goToWorkInformation) // 그 안에서 버튼을 찾음
        workInformationButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_modifyFragment_to_workInformationFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 페이지 이동 - 로그인 화면(로그아웃)
        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            findNavController().navigate(R.id.action_modifyFragment_to_loginFragment)
        }
    }
}