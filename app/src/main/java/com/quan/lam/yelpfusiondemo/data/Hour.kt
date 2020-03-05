package com.quan.lam.yelpfusiondemo.data

data class Hour(
    val hours_type: String,
    val is_open_now: Boolean,
    val `open`: List<Open>
)