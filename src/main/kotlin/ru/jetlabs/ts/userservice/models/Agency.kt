package ru.jetlabs.ts.userservice.models

import java.time.LocalDateTime

data class Agency(
    val id: Long,
    val ownerId: Long,
    val createdAt: LocalDateTime,
)