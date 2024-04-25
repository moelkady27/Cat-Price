package com.example.catprice.ui.auth.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.auth.repository.SignInRepository
import com.example.catprice.ui.auth.viewModel.SignInViewModel

class SignInViewModelFactory(
    private val signInRepository: SignInRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(signInRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}