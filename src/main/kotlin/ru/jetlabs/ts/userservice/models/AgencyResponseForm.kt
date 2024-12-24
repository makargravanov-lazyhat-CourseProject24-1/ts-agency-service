package ru.jetlabs.ts.userservice.models

import java.time.LocalDateTime

data class AgencyResponseForm(
    val id: Long,
    val ownerId: Long,
    val createdAt: LocalDateTime,
)
