package com.example.catprice.ui.setting.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.setting.repository.ProfileRepository
import com.example.catprice.ui.setting.viewModels.ProfileViewModel

class ProfileViewModelFactory(
    private val profileRepository: ProfileRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}