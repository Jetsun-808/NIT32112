package com.project.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.project.myapplication.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
}