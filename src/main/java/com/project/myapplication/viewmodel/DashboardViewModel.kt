package com.project.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.repository.DashboardRepository
import com.project.myapplication.util.Result;
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {
    private val _dashboardResult = MutableLiveData<Result<DashboardResponse>>()
    val dashboardResult: LiveData<Result<DashboardResponse>> = _dashboardResult

    fun getDashboardData(key: String) {
        viewModelScope.launch {
            _dashboardResult.value = Result.Loading
            val result = repository.getDashboardData(key)
            _dashboardResult.value = result
        }
    }
}