package ru.jetlabs.ts.userservice.client

sealed interface AgencyBindResult {
    data object Success : AgencyBindResult
    data class UnknownError(val message: String) : AgencyBindResult
}