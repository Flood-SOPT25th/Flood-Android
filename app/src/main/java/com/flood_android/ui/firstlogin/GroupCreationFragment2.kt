package com.flood_android.ui.firstlogin

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_first_login_without_groupcode1.*
import kotlinx.android.synthetic.main.fragment_first_login_without_groupcode2.*

class GroupCreationFragment2 : Fragment() {

    private var btnFlag = false
    private var bigFlag = 0
    private var smallFlag = -1


    var tvDesign = ArrayList<TextView>()
    var tvMarketing = ArrayList<TextView>()
    var tvBusiness = ArrayList<TextView>()
    var tvIT = ArrayList<TextView>()

    var designClicked = ArrayList<Boolean>()
    var marketingClicked = ArrayList<Boolean>()
    var businessClicked = ArrayList<Boolean>()
    var itClicked = ArrayList<Boolean>()

    var sendCategory = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (i in 0..13) {
            designClicked.add(false)
            itClicked.add(false)
        }

        for (i in 0..6) {
            businessClicked.add(false)
            marketingClicked.add(false)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_first_login_without_groupcode2,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tvBig = arrayListOf<TextView>(
            tv_first_login_without_groupcode2_1,
            tv_first_login_without_groupcode2_2,
            tv_first_login_without_groupcode2_3,
            tv_first_login_without_groupcode2_4
        )

        var cl = arrayListOf<ConstraintLayout>(
            cstlay_first_login_without_groupcode_2_1,
            cstlay_first_login_without_groupcode_2_2,
            cstlay_first_login_without_groupcode_2_3,
            cstlay_first_login_without_groupcode_2_4
        )

        var lv = arrayListOf<View>(
            lv_first_login_1, lv_first_login_2, lv_first_login_3, lv_first_login_4
        )

        //작은 카테고리
        for (m in 0..13) {
            tvDesign.add(
                view.findViewById(
                    R.id.cstlay_first_login_without_groupcode_2_1_1 + m
                )
            )
            tvDesign[m].setOnClickListener {
                if (!designClicked[m]) {
                    tvDesign[m].setTextColor(Color.parseColor("#282828"))
                    tvDesign[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrbold.otf"
                    )
                    sendCategory.add(tvDesign[m].text.toString())
                    designClicked[m] = true
                    (activity as GroupCreationActivity).activateNextBtn(true)
                    toGroupCrAct2()
                } else {
                    tvDesign[m].setTextColor(Color.parseColor("#d1d1d1"))
                    tvDesign[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrmedium.otf"
                    )
                    sendCategory.remove(tvDesign[m].text.toString())
                    designClicked[m] = false
                    //(activity as GroupCreationActivity).activateNextBtn(false)
                }
                Log.v("전송", sendCategory.toString())
            }
        }

        for (m in 0..6) {
            tvMarketing.add(
                view.findViewById(
                    R.id.cstlay_first_login_without_groupcode_2_2_1 + m
                )
            )
            tvMarketing[m].setOnClickListener {
                //  initItem()
                if (!marketingClicked[m]) {
                    tvMarketing[m].setTextColor(Color.parseColor("#282828"))
                    tvMarketing[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrbold.otf"
                    )
                    sendCategory.add(tvMarketing[m].text.toString())
                    marketingClicked[m] = true
                    (activity as GroupCreationActivity).activateNextBtn(true)
                    toGroupCrAct2()
                } else {
                    tvMarketing[m].setTextColor(Color.parseColor("#d1d1d1"))
                    tvMarketing[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrmedium.otf"
                    )
                    sendCategory.remove(tvMarketing[m].text.toString())
                    marketingClicked[m] = false
                    if(sendCategory==null) (activity as GroupCreationActivity).activateNextBtn(false)
                }
                Log.v("전송", sendCategory.toString())
            }
        }

        for (m in 0..6) {
            tvBusiness.add(
                view.findViewById(
                    R.id.cstlay_first_login_without_groupcode_2_3_1 + m
                )
            )
            tvBusiness[m].setOnClickListener {
                Log.v("전송", sendCategory.toString())
                if (!businessClicked[m]) {
                    tvBusiness[m].setTextColor(Color.parseColor("#282828"))
                    tvBusiness[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrbold.otf"
                    )
                    sendCategory.add(tvBusiness[m].text.toString())
                    businessClicked[m] = true
                    (activity as GroupCreationActivity).activateNextBtn(true)
                    toGroupCrAct2()
                } else {
                    tvBusiness[m].setTextColor(Color.parseColor("#d1d1d1"))
                    tvBusiness[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrmedium.otf"
                    )
                    sendCategory.remove(tvBusiness[m].text.toString())
                    businessClicked[m] = false
                    if(sendCategory==null) (activity as GroupCreationActivity).activateNextBtn(false)
                }
            }
        }

        for (m in 0..12) {
            tvIT.add(
                view.findViewById(
                    R.id.cstlay_first_login_without_groupcode_2_4_1 + m
                )
            )
            tvIT[m].setOnClickListener {
                //initItem()
                Log.v("전송", sendCategory.toString())
                if (!itClicked[m]) {
                    tvIT[m].setTextColor(Color.parseColor("#282828"))
                    tvIT[m].typeface =
                        Typeface.createFromAsset(
                            requireContext().assets,
                            "font/notosanscjkkrbold.otf"
                        )
                    sendCategory.remove(tvIT[m].text.toString())
                    itClicked[m] = true
                    (activity as GroupCreationActivity).activateNextBtn(true)
                    toGroupCrAct2()
                } else {
                    tvIT[m].setTextColor(Color.parseColor("#d1d1d1"))
                    tvIT[m].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrmedium.otf"
                    )
                    sendCategory.remove(tvIT[m].text.toString())
                    itClicked[m] = false
                    if(sendCategory==null) (activity as GroupCreationActivity).activateNextBtn(false)
                }
                Log.v("전송", sendCategory.toString())
            }
        }

        // 큰 카테고리

        for (i in 0..3) {
            tvBig[i].setOnClickListener {
                Log.v("클릭", "클릭")
                for (k in 0..3) {
                    cl[k].visibility = View.GONE
                    lv[k].visibility = View.GONE
                    tvBig[k].setTextColor(Color.parseColor("#d1d1d1"))
                    tvBig[k].typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "font/notosanscjkkrmedium.otf"
                    )
                }

                tvBig[i].setTextColor(Color.parseColor("#282828"))
                tvBig[i].typeface = Typeface.createFromAsset(
                    requireContext().assets,
                    "font/notosanscjkkrbold.otf"
                )
                cl[i].visibility = View.VISIBLE
                lv[i].visibility = View.VISIBLE
                bigFlag = i
            }
        }
    }

    fun toGroupCrAct2() {
        (activity as GroupCreationActivity).groupCRInfo.category = sendCategory
    }
}
