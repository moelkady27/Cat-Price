package com.example.catprice.ui.setting.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.auth.viewModel.LogOutViewModel
import com.example.catprice.ui.setting.repository.ChangePasswordRepository
import com.example.catprice.ui.setting.viewModels.ChangePasswordViewModels

class ChangePasswordViewModelFactory(
    private val changePasswordRepository: ChangePasswordRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModels::class.java)) {
            return ChangePasswordViewModels(changePasswordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}