package com.futureproducts.gbrself.models

data class Item(
    val actual_price: String,
    val discount_price: String,
    val imei: String,
    val item_total: String,
    val product_name: String,
    val qty: String,
    val sku: String
)