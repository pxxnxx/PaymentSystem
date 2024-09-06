package com.example.paymentservice.payment.test

import com.example.paymentservice.payment.domain.PaymentEvent

interface PaymentDatabaseHelper {

    fun getPayments(orderId: String): PaymentEvent?
}