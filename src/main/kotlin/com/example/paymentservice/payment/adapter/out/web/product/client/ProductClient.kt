package com.example.paymentservice.payment.adapter.out.web.product.client

import com.example.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

interface ProductClient {

    fun getProduct(cartId: Long, productIds: List<Long>): Flux<Product>
}