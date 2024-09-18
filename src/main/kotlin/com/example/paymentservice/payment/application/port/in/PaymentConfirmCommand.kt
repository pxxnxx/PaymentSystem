package com.example.paymentservice.payment.application.port.`in`

class PaymentConfirmCommand(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
)