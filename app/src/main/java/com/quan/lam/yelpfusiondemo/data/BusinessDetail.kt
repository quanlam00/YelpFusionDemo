package com.quan.lam.yelpfusiondemo.data

data class BusinessDetail(
    val categories: List<Category> = ArrayList(),
    val coordinates: Coordinates = Coordinates(0.0,0.0),
    val hours: List<Hour> = ArrayList(),
    val id: String = "",
    val image_url: String = "",
    val location: Location = Location(),
    val name: String = "",
    val photos: List<String> = ArrayList(),
    val price: String = "",
    val rating: Double = 0.0,
    val review_count: Int = 0,
    val url: String = ""
)