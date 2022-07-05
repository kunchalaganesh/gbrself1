package com.futureproducts.gbrself.models

data class ItemX(
    val actual_price: String,
    val discount_price: String,
    val imeilist: List<Imeilist>,
    val item_total: String,
    val product_name: String,
    val qty: String,
    val sku: String
)