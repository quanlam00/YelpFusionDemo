package com.quan.lam.yelpfusiondemo.data

data class Business(
    val categories: List<Category> = ArrayList(),
    val coordinates: Coordinates = Coordinates(0.0, 0.0),
    val id: String = "",
    val image_url: String = "",
    val location: Location = Location(),
    val name: String = "",
    val phone: String = "",
    val price: String = "",
    val rating: Double = 0.0,
    val review_count: Int = 0,
    val url: String = ""
)