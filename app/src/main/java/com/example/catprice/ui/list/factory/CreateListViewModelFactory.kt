package com.example.catprice.ui.list.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catprice.ui.list.repository.CreateListRepository
import com.example.catprice.ui.list.viewModel.CreateListViewModel

class CreateListViewModelFactory(
    private val createListRepository: CreateListRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateListViewModel::class.java)) {
            return CreateListViewModel(createListRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}