package ru.jetlabs.ts.userservice.service

import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.jetlabs.ts.userservice.AgencyServiceApplication
import ru.jetlabs.ts.userservice.client.AgencyBindRequest
import ru.jetlabs.ts.userservice.client.PaymentClient
import ru.jetlabs.ts.userservice.models.*
import ru.jetlabs.ts.userservice.tables.Agencies
import java.sql.SQLException

@Component
@Transactional
class AgencyService(
    val paymentClient: PaymentClient
) {
    companion object {
        val LOGGER = LoggerFactory.getLogger(AgencyServiceApplication::class.java)!!
    }

    fun registerAgency(registerAgencyForm: AgencyRegisterForm) =
        try {
            Agencies.insertAndGetId {
                it[ownerId] = registerAgencyForm.ownerId
                it[name] = registerAgencyForm.name
            }.value.let {
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
                    name = it[Agencies.name],
                    createdAt = it[Agencies.createdAt],
                )
            )
        } ?: GetByIdResult.NotFound

    fun getByOwnerId(ownerId: Long): GetByOwnerIdResult {
        val agencies = Agencies.selectAll().where { Agencies.ownerId eq ownerId }.map {
            AgencyResponseForm(
                id = it[Agencies.id].value,
                ownerId = it[Agencies.ownerId],
                name = it[Agencies.name],
                createdAt = it[Agencies.createdAt],
            )
        }
        return if (agencies.isEmpty()) {
            GetByOwnerIdResult.NotFound
        } else {
            GetByOwnerIdResult.Success(agencies)
        }
    }

    fun updateBankAccount(updateBankAccountForm: AgencyUpdateBankAccountForm): AgencyUpdateBankAccountResult {
        val response = paymentClient.bindAgency(
            agencyBindRequest = AgencyBindRequest(
                agencyId = updateBankAccountForm.agencyId,
                bankAccountNumber = updateBankAccountForm.bankAccountId
            )
        )

        return if (response.statusCode.is2xxSuccessful)
            AgencyUpdateBankAccountResult.Success
        else
            AgencyUpdateBankAccountResult.UnknownError(response.body.toString()).also {
                LOGGER.error(it.message)
            }
    }
}

