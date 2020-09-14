package adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.byevirus.HotlineData
import com.example.byevirus.lookup.LookUp
import kotlinx.android.synthetic.main.item_look_up.view.*

class LookUpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: LookUp){
        itemView.Label_province_name.text = data.provinceName
        itemView.Label_recovered.text = data.numberOfRecoveredCases
        itemView.Label_confirmed.text = data.numberOfPositiveCases
        itemView.Label_death.text = data.numberOfDeathCases
        //kl misalnya mau dijadiin string bisa dengan ini
        //itemView.Label_recovered.text = "${data.numberOfRecoveredCases}"
    }
}