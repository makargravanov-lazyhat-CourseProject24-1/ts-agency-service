package ru.jetlabs.ts.userservice

import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
class AgencyServiceApplication

fun main(args: Array<String>) {
    runApplication<AgencyServiceApplication>(*args)
}