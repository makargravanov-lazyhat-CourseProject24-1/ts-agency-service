package ru.jetlabs.ts.userservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "ts-payment-service", url = "http://ts-payment-service:8080/ts-payment-service/api/v1")
interface PaymentClient {
    @GetMapping("/bind-agency")
    fun bindAgency(@RequestBody agencyBindRequest: AgencyBindRequest): ResponseEntity<String>
}