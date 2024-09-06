package com.example.paymentservice.payment.domain

import java.math.BigDecimal

data class PaymentOrder (
    val id: Long? = null,
    val paymentEventId: Long? = null,
    val sellerId: Long,
    val productId: Long,
    val orderId: String,
    val amount: BigDecimal,
    val paymentStatus: PaymentStatus,
    private var isLedgerUpdate: Boolean = false,
    private var isWalletUpdate: Boolean = false
) {
    fun isLedgerUpdated(): Boolean = isLedgerUpdate

    fun isWalletUpdated(): Boolean = isWalletUpdate
}
