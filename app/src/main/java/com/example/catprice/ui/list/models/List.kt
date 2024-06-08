package com.example.catprice.ui.list.models

data class List(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isActive: Boolean,
    val listName: String,
//    val listOfItems: List<Any>,
    val numOfItems: Int,
    val totalPrice: Int,
    val type: String,
    val userId: String
)