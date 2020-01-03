package com.flood_android.ui.firstlogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flood_android.R
import com.flood_android.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.fragment_first_login_without_groupcode1.*
import kotlinx.android.synthetic.main.fragment_signup1.*

class FirstLoginFragmentWithoutGroupcode1 : Fragment() {

    private lateinit var joblog: GroupCreationJobSelectDialog
    var nameFlag = false
    var contactFlag = false
    var jobFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_first_login_without_groupcode1,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        joblog = GroupCreationJobSelectDialog {
            tv_first_login_withoutgroupcode1_job_2.text = it
        }

        tv_first_login_withoutgroupcode1_job_2.setOnClickListener {
            joblog.show(activity!!.supportFragmentManager, null)
        }

        edtxt_first_login_withoutgroupcode1_group_name.addTextChangedListener(nameWatcher)
        edtxt_first_login_withoutgroupcode1_group_contact.addTextChangedListener(contactWatcher)
        tv_first_login_withoutgroupcode1_job_2.addTextChangedListener(jobWatcher)
    }

    fun toSignal(flag: Boolean) {
        (activity as GroupCreationActivity).activateNextBtn(flag)
    }

    private val nameWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                nameFlag = true

                if (contactFlag && jobFlag) {
                    toSignal(true)
                    toGroupCrAct1()
                } else {
                    toSignal(false)
                }
            } else {
                toSignal(false)
                nameFlag = false
            }
        }
    }

    private val contactWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                contactFlag = true

                if (nameFlag && jobFlag) {
                    toSignal(true)
                    toGroupCrAct1()
                } else {
                    toSignal(false)
                }
            } else {
                toSignal(false)
                contactFlag = false
            }
        }
    }

    private val jobWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                jobFlag = true

                if (nameFlag && contactFlag) {
                    toSignal(true)
                    toGroupCrAct1()
                } else {
                    toSignal(false)
                }
            } else {
                toSignal(false)
                jobFlag = false
            }
        }
    }

    fun toGroupCrAct1() {
        (activity as GroupCreationActivity).groupCRInfo.name = edtxt_first_login_withoutgroupcode1_group_name.text.toString()
        (activity as GroupCreationActivity).groupCRInfo.phone = edtxt_first_login_withoutgroupcode1_group_contact.text.toString()
        (activity as GroupCreationActivity).groupCRInfo.department = tv_first_login_withoutgroupcode1_job_2.text.toString()
    }
}
