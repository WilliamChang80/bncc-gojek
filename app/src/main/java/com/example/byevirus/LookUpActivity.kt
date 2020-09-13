package UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.byevirus.R
import com.example.byevirus.lookup.LookUp
import kotlinx.android.synthetic.main.activity_look_up.*
import lookup.LookUpAdapter

class LookUpActivity: AppCompatActivity() {
    private val mockLookUpList = mutableListOf(
        LookUp( provinceName = "DKI Jakarta"),
        LookUp( provinceName = "Sumatera"),
        LookUp( provinceName = "Papua"),
        LookUp( provinceName = "Jawa Timur")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_up)

        val lookUpAdapter = LookUpAdapter(mockLookUpList)
        rvlookup.layoutManager = LinearLayoutManager(this)
        rvlookup.adapter = lookUpAdapter

    }
}