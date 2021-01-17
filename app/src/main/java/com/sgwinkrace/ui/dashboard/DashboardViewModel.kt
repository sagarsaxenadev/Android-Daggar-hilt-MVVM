package com.sgwinkrace.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sgwinkrace.ui.fragments.HomeRepository

class DashboardViewModel @ViewModelInject constructor(val homeRepository: HomeRepository) : ViewModel(){
}