package com.sgwinkrace.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sgwinkrace.adapter.HomePostListAdapter
import com.sgwinkrace.adapter.TitleAdapter
import com.sgwinkrace.databinding.FragmentHomeBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.model.HomeListData
import com.sgwinkrace.ui.register_trade.form1.RegisterTradeOneActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {


    @Inject
    lateinit var appDataStore: AppDataStore
    lateinit var binding: FragmentHomeBinding
    var titleList = arrayListOf<String>()

    val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            appDataStore.userIsProfileComplete.collect {
                isProfileComplete = it
            }
            appDataStore.userIdFlow.collect {
                userId = it
            }

            appDataStore.userMobileFlow.collect {
                userMobile = it
            }

            appDataStore.userEmailFlow.collect {
                userEmail = it
            }
        }

        titleList.add("Buy/Sell")
        titleList.add("Buy")
        titleList.add("Sell")
        titleList.add("Product Category")
        titleList.add("Packing")
        titleList.add("Color")


        val titleAdapter = TitleAdapter(requireActivity(), titleList)
        binding.rvTitle.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = titleAdapter
        }

        binding.tvRegisterTrade.setOnClickListener {
            Intent(requireContext(), RegisterTradeOneActivity::class.java).also {
                startActivity(it)
            }
        }

        viewModel.homePost("3","Sell")
binding.progress.visibility=View.VISIBLE
        viewModel.homePostLiveData.observe(requireActivity(),{ response ->
            if (response.sTATUS == "SUCCESS") {
val list=response.pOSTLIST
                binding.progress.visibility=View.GONE

                setToView(list)


            }else {
                binding.progress.visibility=View.GONE

            }

        })

    }

    private fun setToView(list: List<HomeListData.POSTLIST>) {

        val homePostList=HomePostListAdapter(requireActivity(),list)
        binding.rvPostList.apply {
            this.layoutManager=LinearLayoutManager(requireContext())
            adapter=homePostList
        }

    }

    var isProfileComplete = false
    var userId = ""
    var userMobile = ""
    var userEmail = ""
    override fun onResume() {
        super.onResume()


        isProfileComplete?.let {
            if (it) {
                profileCard.visibility = View.GONE
            } else {
                profileCard.visibility = View.VISIBLE
            }
        }
    }
}