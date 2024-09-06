package com.example.paymentservice.payment.adapter.`in`.web.view

import com.example.paymentservice.common.IdempotencyCreator
import com.example.paymentservice.common.WebAdapter
import com.example.paymentservice.payment.adapter.`in`.web.request.CheckoutRequest
import com.example.paymentservice.payment.application.port.`in`.CheckoutCommand
import com.example.paymentservice.payment.application.port.`in`.CheckoutUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
@WebAdapter
class CheckoutController(
    private val checkoutUseCase: CheckoutUseCase
){


    @GetMapping("/")
    fun checkoutPage(request: CheckoutRequest, model: Model): Mono<String> {
        val command = CheckoutCommand(
            cardId = request.cartId,
            buyerId = request.buyerId,
            productIds = request.productIds,
            idempotencyKey = IdempotencyCreator.create(request.seed) // 원래 request 자체가 와야함
        )

        return checkoutUseCase.checkout(command)
            .map {
                model.addAttribute("orderId", it.orderId)
                model.addAttribute("orderName", it.orderName)
                model.addAttribute("amount", it.amount)
                "checkout"
            }
    }
}