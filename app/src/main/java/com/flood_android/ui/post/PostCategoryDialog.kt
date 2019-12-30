package com.flood_android.ui.post


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.flood_android.R
import com.flood_android.ui.postnourl.PostNoUrlActivity
import com.flood_android.util.GlobalData
import kotlinx.android.synthetic.main.activity_bookmark_edit.*
import kotlinx.android.synthetic.main.fragment_post_category_dialog.*

class PostCategoryDialog : RoundedBottomSheetDialogFragment(), View.OnClickListener {
    override fun onClick(v: View?) {


    }

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
        var c = listOf("IT", "마케팅", "마케팅", "마케팅", "IT", "마케팅", "디자인", "마케팅", "서버", "안드로이드", "마케팅", "디자인", "IOS", "기획")
        //
        adapter = PostSelectCategoryDialogRVAdapter(context!!, c, check)
        adapter.name = "cjdgk"

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


//                view.findViewById<TextView>(R.id.tv_rv_item_post_select_category).setTextColor(Color.parseColor("#282828"))
                /*if(rv_bookmark_edit_list.getChildAdapterPosition(view.parent as View) == selectedIdx){
                    view.findViewById<TextView>(R.id.tv_rv_item_post_select_category).setTextColor(Color.parseColor("#282828"))
                }*/

//                for (i in 0 )
                //Log.e("세은", rv_bookmark_edit_list.getChildAdapterPosition(view.parent as View).toString())
                Log.e("세은", position.toString())
//                if (selectedIdx > -1){
//
//                }
            }
        }
        //
        // 리싸이클러뷰
        rv_post_select_category_dialog_list.layoutManager = LinearLayoutManager(context)
        rv_post_select_category_dialog_list.adapter = adapter

        /*postSelectCategoryDialogRVAdapter = PostSelectCategoryDialogRVAdapter(context!!, c)
        rv_post_select_category_dialog_list.layoutManager = LinearLayoutManager(context)
        rv_post_select_category_dialog_list.adapter = postSelectCategoryDialogRVAdapter*/
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

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
