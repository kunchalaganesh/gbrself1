package com.futureproducts.gbrself.models

data class ordermodel1(
    val address: String,
    val dse_id: Int,
    val items: List<ItemXX>,
    val order_total: String,
    val ret_id: Int,
    val paid_amount : String,
    val payment_mode: String,
    val payment_type : String,
    val payment_description : String

)