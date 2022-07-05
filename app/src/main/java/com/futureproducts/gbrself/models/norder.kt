package com.futureproducts.gbrself.models

data class norder(
    val address: String,
    val dse_id: Int,
    val items: List<ItemX>,
    val order_total: String,
    val ret_id: Int
)