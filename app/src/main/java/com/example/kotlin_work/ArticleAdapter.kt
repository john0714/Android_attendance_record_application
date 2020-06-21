package com.example.kotlin_work

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// inflater프로퍼티 생성
class ArticleAdapter(private val inflater: LayoutInflater): ListAdapter<MainFragment.Article, ArticleAdapter.ViewHolder>(ArticleAdapter.diffCallback) { // 앞에께 property constructor(인수)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 행 하나의 View를 만들고, ViewHolder의 형태로 해서 반환하게 만듬
        val view = inflater.inflate( // View는 레이아웃 XML경유로 작성하는게 좋으므로 LayoutInflater를 사용해서 만들게 하자
            R.layout.item_article, parent, false) // 행 하나분의 레이아웃이 되는 item_article.xml을 만들어서 여기에 넣어주자.
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // ViewHolder를 받아서 View에 값을 셋팅함
        // 데이터 받음
        val article = getItem(position)

        // 데이터의 중심에 View를 셋팅
        holder.titleText.text = article.title
        holder.bodyText.text = article.body
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<MainFragment.Article>() {
            override fun areItemsTheSame(oldItem: MainFragment.Article, newItem: MainFragment.Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MainFragment.Article, newItem: MainFragment.Article): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

    //view에 넣는것
    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val titleText = item.findViewById<TextView>(R.id.text_title)
        val bodyText = item.findViewById<TextView>(R.id.text_body)
    }
}