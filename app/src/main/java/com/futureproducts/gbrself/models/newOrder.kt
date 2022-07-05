package com.futureproducts.gbrself.models

data class newOrder(
    val error: Boolean,
    val error_code: Int,
    val list: List<likes>,
    val message: String
)