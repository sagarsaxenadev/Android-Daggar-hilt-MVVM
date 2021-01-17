package com.sgwinkrace.ui.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgwinkrace.model.HomeListData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject
constructor(val homeRepository: HomeRepository) : ViewModel(){

    private var _homePostLiveData = MutableLiveData<HomeListData>()

    val homePostLiveData: LiveData<HomeListData>
        get() = _homePostLiveData


    fun homePost(id:String,type:String) {

        viewModelScope.launch {

            homeRepository.homePost(id,type).collect {
                _homePostLiveData.value=it
            }

        }
    }
}