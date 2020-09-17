package com.example.byevirus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.byevirus.entity.Hotline
import com.example.byevirus.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_hotline_bottom_sheet.*
import com.example.byevirus.adapter.HotlineAdapter
import com.example.byevirus.contract.HotlineContract
import com.example.byevirus.model.HotlineModel
import com.example.byevirus.presenter.HotlinePresenter

class BottomSheetFragment : BottomSheetDialogFragment(), HotlineContract.View {

    private lateinit var skeletonScreen: SkeletonScreen
    private lateinit var hotlineAdapter: HotlineAdapter
    private lateinit var hotlinePresenter: HotlinePresenter

    companion object {
        const val NUMBER_OF_LOADING_ITEMS = 7
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_hotline_bottom_sheet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotlinePresenter = HotlinePresenter(HotlineModel(), this)
        initializeAdapter()
        initializeCloseButton()
        initializeLoadingComponents()
        hotlinePresenter.getData()
    }

    private fun initializeAdapter() {
        val mockHotlineList: MutableList<Hotline> = mutableListOf()
        hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvhotline.layoutManager = LinearLayoutManager(context)
        rvhotline.adapter = hotlineAdapter
    }

    private fun initializeLoadingComponents() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rvhotline)
        skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(hotlineAdapter)
            .load(R.layout.hotline_view_skeleton)
            .count(NUMBER_OF_LOADING_ITEMS).show()
    }

    private fun initializeCloseButton() {
        view?.findViewById<ImageView>(R.id.Image_X)?.setOnClickListener {
            this@BottomSheetFragment.dismiss()
        }
    }

    override fun updateData(hotlineList: MutableList<Hotline>) {
        stopLoading()
        hotlineAdapter.updateData(hotlineList)
    }

    override fun onError(error: Exception) {
        activity?.runOnUiThread {
            Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun startLoading() {
        activity?.runOnUiThread {
            skeletonScreen.show()
        }
    }

    override fun stopLoading() {
        activity?.runOnUiThread {
            skeletonScreen.hide()
        }
    }
}