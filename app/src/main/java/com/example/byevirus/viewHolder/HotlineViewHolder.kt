package com.example.byevirus.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.byevirus.HotlineData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hotline.view.*

class HotlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: HotlineData){
        itemView.TextView_nama_lembaga.text = data.name
        if(data.imgIcon.isNotBlank()){
            Picasso.get().load(data.imgIcon).into(itemView.Image_Lembaga)
        }
        itemView.TextView_phone.text = data.phone

        //itemView.Label_recovered.text = data.numberOfRecoveredCases
        //kl misalnya mau dijadiin string bisa dengan ini
        //itemView.Label_recovered.text = "${data.numberOfRecoveredCases}"
    }
}