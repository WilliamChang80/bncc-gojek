package com.example.byevirus.adapter

import android.util.Log
import com.example.byevirus.viewHolder.LookUpViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.byevirus.R
import com.example.byevirus.model.LookUp
import java.util.logging.Logger

class LookUpAdapter
    (private val lookUpList: MutableList<LookUp>) : RecyclerView.Adapter<LookUpViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookUpViewHolder {

        return LookUpViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_look_up,
                parent,
                false
            )
        )
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

    fun filterData(search: String) {
        val filteredLookupList: List<LookUp> = lookUpList.filter { lookUp ->
            lookUp.provinceName.contains(search, true)
        }
        updateData(filteredLookupList as MutableList<LookUp>)
    }

}