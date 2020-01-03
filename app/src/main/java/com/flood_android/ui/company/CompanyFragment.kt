package com.flood_android.ui.company


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_company.*

class CompanyFragment : Fragment() {


    lateinit var companyRVAdapter: CompanyRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.v("postygyg", "postygygasdfkla;sjfdl")
        getCompany()

    }

    private fun getCompany() {
        val getCompanyResponse = ApplicationController.networkServiceFeed
            .getCompanyResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58")
        getCompanyResponse.safeEnqueue {
            Log.v("postygyg", "postygyg1234")
            if (it.message == "그룹 리스트") {
                Log.v("postygyg", "postygyg12342344")
                setRecyclerView(it.data.groupArr)
            }
        }

    }

    private fun setRecyclerView(dataList: List<GroupArr>) {
        Log.v("postygyg", "setRecyclerview")

        companyRVAdapter = CompanyRVAdapter(context!!, dataList)
        rv_company_list.adapter = companyRVAdapter
        rv_company_list.layoutManager = LinearLayoutManager(context!!)
    }
}
