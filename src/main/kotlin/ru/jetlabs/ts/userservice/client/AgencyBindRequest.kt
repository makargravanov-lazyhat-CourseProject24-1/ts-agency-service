package ru.jetlabs.ts.userservice.client

data class AgencyBindRequest(
    val agencyId: Long,
    val bankAccountNumber: String,
)
