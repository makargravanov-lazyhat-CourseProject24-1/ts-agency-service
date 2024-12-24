package ru.jetlabs.ts.userservice

import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
@EnableFeignClients
class AgencyServiceApplication

fun main(args: Array<String>) {
    runApplication<AgencyServiceApplication>(*args)
}