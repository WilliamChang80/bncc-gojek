package com.example.byevirus.adapter

import com.example.byevirus.adapter.HotlineViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.byevirus.HotlineData
import com.example.byevirus.R

class HotlineAdapter(private val hotlineList: MutableList<HotlineData>): RecyclerView.Adapter<HotlineViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotlineViewHolder {

        return HotlineViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.item_hotline,
            parent,
            false))
    }

    override fun getItemCount(): Int {
        return hotlineList.size
    }

    override fun onBindViewHolder(holder: HotlineViewHolder, position: Int) {
        holder.bind(hotlineList[position])
    }

    fun updateData(newList: MutableList<HotlineData>) {
        hotlineList.clear()
        hotlineList.addAll(newList)
        notifyDataSetChanged()
    }

}