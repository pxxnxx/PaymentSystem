package com.example.paymentservice.payment.adapter.out.persistent.repository

import reactor.core.publisher.Mono

interface PaymentStatusUpdateRepository {

    fun updatePaymentStatusToExecute(orderId: String, paymentKey: String) : Mono<Boolean>
}