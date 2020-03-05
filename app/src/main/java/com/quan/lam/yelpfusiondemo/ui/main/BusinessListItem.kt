package com.quan.lam.yelpfusiondemo.ui.main
//Simplify Model of a Business. Only hold information needed for a List Item
data class BusinessListItem(val name: String,
                            val image_url: String,
                            val rating: Double,
                            val id: String)