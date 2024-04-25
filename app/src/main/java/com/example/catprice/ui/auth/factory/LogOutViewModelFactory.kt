package com.example.catprice.ui.auth.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.auth.repository.LogOutRepository
import com.example.catprice.ui.auth.viewModel.LogOutViewModel
import com.example.catprice.ui.auth.viewModel.SignInViewModel

class LogOutViewModelFactory(
    private val logOutRepository: LogOutRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogOutViewModel::class.java)) {
            return LogOutViewModel(logOutRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}