package com.example.catprice.ui.auth.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.auth.repository.SignUpRepository
import com.example.catprice.ui.auth.viewModel.SignUpViewModel

class SignUpViewModelFactory(
    private val signUpRepository: SignUpRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(signUpRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}