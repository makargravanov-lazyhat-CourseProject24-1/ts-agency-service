package ru.jetlabs.ts.userservice.service

import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.jetlabs.ts.userservice.AgencyServiceApplication
import ru.jetlabs.ts.userservice.models.*
import ru.jetlabs.ts.userservice.tables.Agencies
import java.sql.SQLException

@Component
@Transactional
class AgencyService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(AgencyServiceApplication::class.java)!!
    }

    fun registerAgency(registerAgencyForm: AgencyRegisterForm) =
        try {
            Agencies.insertAndGetId { it[ownerId] = registerAgencyForm.ownerId }.value.let {
                RegisterResult.Success(id = it)
            }
        } catch (e: SQLException) {
            RegisterResult.UnknownError(message = e.stackTraceToString())
        }


    fun getById(id: Long): GetByIdResult =
        Agencies.selectAll().where { Agencies.id eq id }.singleOrNull()?.let {
            GetByIdResult.Success(
                AgencyResponseForm(
                    id = it[Agencies.id].value,
                    ownerId = it[Agencies.ownerId],
                    createdAt = it[Agencies.createdAt],
                )
            )
        } ?: GetByIdResult.NotFound

    fun updateBankAccount(updateBankAccountForm: AgencyUpdateBankAccountForm): AgencyUpdateBankAccountResult {
        TODO("payment bind request")
    }
}

