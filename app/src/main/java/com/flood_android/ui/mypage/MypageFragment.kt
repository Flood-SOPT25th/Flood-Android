package com.flood_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.Jihee.MypageMyflipRvItem
import com.flood_android.ui.bookmarkedit.BookmarkEditActivity
import com.flood_android.ui.mypage.adapter.MyPageRVViewHolder
import com.flood_android.util.onSingleClickListener
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {
    private lateinit var rvmypagemyflip: RecyclerView
   private lateinit var mypageRvAdapter : MyPageRVAdapter
   private lateinit var mypageMyflipdata : List<MypageMyflipRvItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initmyflipList()
        /**
         *
         */
        //get data from server here 통신이 들어올 때마다 notify 될 것
        //서버에서 데이터 객체 받아와서 성공 시 extension 함수 이용해 호출하고 받은 데이터 객체를
        //ApplicationController.instance.networkServiceUser.enqueue(mypageMyflipdata)

        imgbtn_mypage_myflip_plus.setOnClickListener(object : onSingleClickListener() {
            override fun onSingleClick(v: View) {
                var intent = Intent(context, BookmarkEditActivity::class.java)
                startActivity(intent)
            }
        })

    }

    private fun initmyflipList() {
        mypageRvAdapter = MyPageRVAdapter()
        rvmypagemyflip = rv_mypage_myflip
        rvmypagemyflip.adapter = mypageRvAdapter
    }


    private inner class MyPageRVAdapter() : RecyclerView.Adapter<MyPageRVViewHolder>() {
        private var data = listOf<MypageMyflipRvItem>()//parameter--var data : List<MypageMyflipRvItem>

        fun setDataWithNotify(data: List<MypageMyflipRvItem>) {
            rvplaceholder_mypage_myflip.isVisible = data.isEmpty()
            this.data = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageRVViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.rv_item_mypage_myflip, parent, false)
            return MyPageRVViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyPageRVViewHolder, position: Int) {
            holder.bind(data[position])
        }
    }



//private fun setData(item : List<MyPageRvItem>) {
//        Adapter.data = item
//        secAdapter.notifyDataSetChanged()
//    }
//
}
