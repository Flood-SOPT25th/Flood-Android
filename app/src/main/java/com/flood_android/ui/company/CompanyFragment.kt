package com.flood_android.ui.company

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_company.*

class CompanyFragment : Fragment() {

    lateinit var companyRVAdapter: CompanyRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getCompany()

    }

    private fun getCompany() {
        val getCompanyResponse = ApplicationController.networkServiceFeed
            .getCompanyResponse(SharedPreferenceController.getAuthorization(context!!)!!)
        getCompanyResponse.safeEnqueue {
            if (it.message == "그룹 리스트") {
                setRecyclerView(it.data.groupArr)
            }
        }
    }

    private fun setRecyclerView(dataList: List<GroupArr>) {
        companyRVAdapter = CompanyRVAdapter(context!!, dataList)
        rv_company_list.adapter = companyRVAdapter
        rv_company_list.layoutManager = LinearLayoutManager(context!!)
    }
}
