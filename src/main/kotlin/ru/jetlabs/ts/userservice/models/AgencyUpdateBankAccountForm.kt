package ru.jetlabs.ts.userservice.models

data class AgencyUpdateBankAccountForm(
    val agencyId: Long,
    val bankAccountId: String,
)
