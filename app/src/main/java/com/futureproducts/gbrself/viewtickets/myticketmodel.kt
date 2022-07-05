package com.futureproducts.gbrself.viewtickets

data class myticketmodel(
    val error: Boolean,
    val error_code: Int,
    val message: String,
    val support_tickets: List<SupportTicket>
)