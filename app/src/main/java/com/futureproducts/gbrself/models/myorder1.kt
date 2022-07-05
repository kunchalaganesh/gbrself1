package com.futureproducts.gbrself.models

data class myorder1(
    val error: Boolean,
    val error_code: Int,
    val list: List<myorders>,
    val message: String
)