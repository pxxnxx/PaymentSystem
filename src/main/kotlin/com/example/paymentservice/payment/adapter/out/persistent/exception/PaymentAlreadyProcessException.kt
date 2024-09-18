package com.example.paymentservice.payment.adapter.out.persistent.exception

import com.example.paymentservice.payment.domain.PaymentStatus

class PaymentAlreadyProcessException(
    val status: PaymentStatus,
    message: String
) : RuntimeException(message)