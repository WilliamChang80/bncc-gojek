package com.example.byevirus.adapter

import com.example.byevirus.adapter.LookUpViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.byevirus.R
import com.example.byevirus.lookup.LookUp

class LookUpAdapter
    (private val lookUpList: MutableList<LookUp>): RecyclerView.Adapter<LookUpViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookUpViewHolder {

            return LookUpViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_look_up,
                parent,
                false))
        }

        override fun getItemCount(): Int {
            return lookUpList.size
        }

        override fun onBindViewHolder(holder: LookUpViewHolder, position: Int) {
            holder.bind(lookUpList[position])
        }
    fun updateData(newList: MutableList<LookUp>) {
        lookUpList.clear()
        lookUpList.addAll(newList)
        notifyDataSetChanged()
    }


}