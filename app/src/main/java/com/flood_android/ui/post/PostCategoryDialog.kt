package com.flood_android.ui.post

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.flood_android.R
import com.flood_android.ui.postnourl.PostNoUrlActivity
import com.flood_android.util.GlobalData
import kotlinx.android.synthetic.main.fragment_post_category_dialog.*

class PostCategoryDialog : RoundedBottomSheetDialogFragment() {

    lateinit var adapter: PostSelectCategoryDialogRVAdapter
    var selectedIdx = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_category_dialog, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 카테고리 리싸이클러뷰
        categoryRecyclerView(GlobalData.categoryList)
        setBtnClickListner()
    }

    /**
     * 카테고리 리싸이클러 뷰
     */
    var check = arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)

    private fun categoryRecyclerView(categoryList: List<String>) {
        adapter = PostSelectCategoryDialogRVAdapter(context!!, categoryList, check)
        adapter.itemClick = object: PostSelectCategoryDialogRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                tv_post_select_category_dialog_done.setTextColor(Color.parseColor("#0057ff"))
                if(selectedIdx > -1) {
                    check[selectedIdx] = false
                    adapter.changeflagData(check)
                    adapter.notifyItemChanged(selectedIdx)
                }
                selectedIdx = position
                check[selectedIdx] = true
                adapter.changeflagData(check)
                adapter.notifyItemChanged(selectedIdx)
            }
        }
        // 리싸이클러뷰
        rv_post_select_category_dialog_list.layoutManager = LinearLayoutManager(context)
        rv_post_select_category_dialog_list.adapter = adapter
    }

    /**
     * 버튼 클릭 함수
     */
    private fun setBtnClickListner() {
        tv_post_select_category_dialog_done.setOnClickListener {
            if(check.contains(true)){
                var category : String? = GlobalData.selectedCategory
                if ( GlobalData.categoryDialogFalg == "0"){
                    (context as PostActivity).setCategory(category)
                }else {
                    (context as PostNoUrlActivity).setNoUrlCategory(category)
                }
                dismiss()
            }
        }
    }
}
