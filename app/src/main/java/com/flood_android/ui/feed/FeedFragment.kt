package com.flood_android.ui.feed


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.flood_android.R
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.toast_feed_save_flips_category.*


class FeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Feed 탭 처음에 flood 카테고리 화면을 띄우도록
        setInvisible(cl_feed_no_news) // 게시물이 없는 화면 안보이게
        val transaction: FragmentTransaction = (context as MainActivity).supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_feed_fragment_frag, FeedFloodFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setOnClickListener(){
        btn_feed_go_post.setOnClickListener (object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                //val intent = Intent(context!!, PostActivity:;class.java)
                //context.startActivity(intent)
            }
        })
    }

    private fun makeToast(category : String){
        var inflater : LayoutInflater = layoutInflater
        val toastDesign = inflater.inflate(
            R.layout.toast_feed_save_flips_category,
            (R.id.cl_feed_save_flips_category) as ViewGroup
        )
        tv_feed_save_flips_category.text = category

        var toast : Toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0)  //center를 기준으로 0 0 위치에 메시지 출력
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastDesign
        toast.show()
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }

}
