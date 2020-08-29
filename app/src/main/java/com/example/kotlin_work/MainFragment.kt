package com.example.kotlin_work

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import okhttp3.OkHttpClient

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainFragment : Fragment() {
    // private lateinit var adapter: ArticleAdapter;
    private val handler = Handler()
    private val client = OkHttpClient()
    private val loginVM: LoginViewModel by activityViewModels()
    val mainVM: MainViewModel by viewModels()

    val viewModel: MainViewModel by viewModels( factoryProducer = {
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(
                    OkHttpClient::class.java
                ).newInstance(client)
            }
        }
    })

    data class Article(val id: Int, val title: String, val body: String)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false) // view 오브젝트를 생성함(fragment_main)
        
        // 페이지 이동 - 근무 수정
        val modifyButton= view.findViewById<Button>(R.id.button_goToModify) // 그 안에서 버튼을 찾음
        modifyButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_modifyFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 유급 정보
        val vacationInformationButton = view.findViewById<Button>(R.id.button_goToVacationInformation) // 그 안에서 버튼을 찾음
        vacationInformationButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_vacationInformationFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 근무 정보
        val workInformationButton = view.findViewById<Button>(R.id.button_goToWorkInformation) // 그 안에서 버튼을 찾음
        workInformationButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_workInformationFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }

        // 페이지 이동 - 로그인 화면(로그아웃)
        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        // 출근 / 퇴근
        view.findViewById<Button>(R.id.button_stamping).setOnClickListener {
            mainVM.doStamping("start", loginVM.userToken)
        }
        
        // 레이아웃 매니저 세팅
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler) // 리사이클러 뷰를 찾음
//        recyclerView.layoutManager = LinearLayoutManager(
//            inflater.context,
//            LinearLayoutManager.VERTICAL, // 나열 방향
//            false // 역순으로 할꺼면 true를 세팅
//        )
//
//        // 어댑터 셋팅
//        this.adapter = ArticleAdapter(inflater)
//        recyclerView.adapter = this.adapter
//
//        // 테스트 데이터 셋팅
//        this.adapter.submitList(mutableListOf(
//            Article(1, "Title1", "Body1"),
//            Article(2, "Title2", "Body2"),
//            Article(3, "Title3", "List Test3")
//        ))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 에러 회피를 위해 필요함
        viewModel.responseBody.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),
                it, Toast.LENGTH_LONG).show()
        })

        val timeTextView = view.findViewById<TextView>(R.id.Time)
        mainVM.timeLiveData.observe(viewLifecycleOwner, Observer {
            timeTextView.text = mainVM.timeLiveData.value
        })

        handler.post(object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                mainVM.setNowTime()
            }

        })
    }
}
