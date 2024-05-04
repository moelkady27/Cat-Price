package com.example.catprice.ui.setting.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.setting.repository.ContactUsRepository
import com.example.catprice.ui.setting.viewModels.ContactUsViewModel

class ContactUsViewModelFactory(
    private val contactUsRepository: ContactUsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactUsViewModel::class.java)) {
            return ContactUsViewModel(contactUsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}