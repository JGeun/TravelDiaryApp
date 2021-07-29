package com.hansung.traveldiary.src.uploadDiary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R

class ViewPagerAdapter(private val list: ArrayList<Int>) :



    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.diary, parent, false) as ViewGroup
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(list[position], position)

    }

    override fun getItemCount(): Int = item.size

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.diary, parent, false)) {
        private val textView: TextView = itemView.findViewById(R.id.upDiary_text)

        fun bind(bgColor: Int, position: Int) {
            itemView.setBackgroundColor(getColor(itemView.context, bgColor))
        }
    }
}

