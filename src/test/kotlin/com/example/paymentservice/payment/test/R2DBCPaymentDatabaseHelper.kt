package com.example.paymentservice.payment.test

import com.example.paymentservice.payment.domain.PaymentEvent
import com.example.paymentservice.payment.domain.PaymentOrder
import com.example.paymentservice.payment.domain.PaymentStatus
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.kotlin.core.publisher.toMono
import java.math.BigDecimal

class R2DBCPaymentDatabaseHelper(
    private val databaseClient: DatabaseClient,
    private val transactionalOperator: TransactionalOperator
) : PaymentDatabaseHelper {

    override fun getPayments(orderId: String): PaymentEvent? {
        return databaseClient.sql(SELECT_PAYMENT_QUERY)
            .bind("orderId", orderId)
            .fetch()
            .all()
            .groupBy { it["payment_event_id"] as Long }
            .flatMap { groupFlux ->
                groupFlux.collectList().map { results ->
                    PaymentEvent(
                        id = groupFlux.key(),
                        orderId = results.first()["order_id"] as String,
                        orderName = results.first()["order_name"] as String,
                        buyerId = results.first()["buyer_id"] as Long,
                        isPaymentDone = (results.first()["is_payment_done"] as Byte).toInt() == 1,
                        paymentOrders = results.map { result ->
                            PaymentOrder(
                                id = result["id"] as Long,
                                paymentEventId = groupFlux.key(),
                                sellerId = result["seller_id"] as Long,
                                orderId = result["order_id"] as String,
                                productId = result["product_id"] as Long,
                                amount = result["amount"] as BigDecimal,
                                paymentStatus = PaymentStatus.get(result["payment_order_status"] as String),
                                isLedgerUpdate = (result["ledger_updated"] as Byte).toInt() == 1,
                                isWalletUpdate = (result["wallet_updated"] as Byte).toInt() == 1
                            )

                        }

                    )
                }
            }.toMono().block()
    }


    companion object {
        val SELECT_PAYMENT_QUERY = """
            SELECT * FROM payment_events pe
            INNER JOIN payment_orders po ON pe.order_id = po.order_id
            WHERE pe.order_id = :orderId
        """.trimIndent()
    }
}