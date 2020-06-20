package com.example.kotlin_work

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var adapter: ArticleAdapter;

    data class Article(val id: Int, val title: String, val body: String)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false) // view 오브젝트를 생성함(fragment_second)

        // 페이지 이동
        val b = view.findViewById<Button>(R.id.goThirdFragmentButton) // 그 안에서 버튼을 찾음
        b.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_SecondFragment_to_detailFragment) // 찾은 버튼을 눌렀을때 일어날 action을 지정함 → navigate의 화살표
        }
        
        // 레이아웃 매니저 세팅
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler) // 리사이클러 뷰를 찾음
        recyclerView.layoutManager = LinearLayoutManager(
            inflater.context,
            LinearLayoutManager.VERTICAL, // 나열 방향
            false // 역순으로 할꺼면 true를 세팅
        )

        // 어댑터 셋팅
        this.adapter = ArticleAdapter(inflater)
        recyclerView.adapter = this.adapter

        // 테스트 데이터 셋팅
        this.adapter.submitList(mutableListOf(
            Article(1, "Title1", "Body1"),
            Article(2, "Title2", "Body2"),
            Article(3, "Title3", "List Test3")
        ))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.goFirstFragmentButton).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}
