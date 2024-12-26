package ru.jetlabs.ts.userservice.models

sealed interface RegisterResult {
    data class Success(val id: Long) : RegisterResult
    data class UnknownError(val message: String) : RegisterResult
}

sealed interface GetByIdResult {
    data class Success(val agencyResponseForm: AgencyResponseForm) : GetByIdResult
    data object NotFound : GetByIdResult
}

sealed interface GetByOwnerIdResult {
    data class Success(val agencies: List<AgencyResponseForm>) : GetByOwnerIdResult
    data object NotFound : GetByOwnerIdResult
}

sealed interface AgencyUpdateBankAccountResult {
    data object Success : AgencyUpdateBankAccountResult
    data class UnknownError(val message: String) : AgencyUpdateBankAccountResult
}