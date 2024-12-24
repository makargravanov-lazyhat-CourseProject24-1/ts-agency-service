package ru.jetlabs.ts.userservice.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.jetlabs.ts.userservice.models.AgencyRegisterForm
import ru.jetlabs.ts.userservice.models.AgencyUpdateBankAccountForm
import ru.jetlabs.ts.userservice.models.GetByIdResult
import ru.jetlabs.ts.userservice.models.RegisterResult
import ru.jetlabs.ts.userservice.service.AgencyService

@RestController
@RequestMapping("/ts-agency-service/api/v1")
class AgencyServiceController(
    private val agencyService: AgencyService
) {
    @PostMapping("/agency")
    fun registerAgency(@RequestBody registerAgencyForm: AgencyRegisterForm): ResponseEntity<*> =
        agencyService.registerAgency(registerAgencyForm).let {
            when (it) {
                is RegisterResult.Success -> ResponseEntity.status(HttpStatus.OK).body(it.id)
                is RegisterResult.UnknownError -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it)
            }
        }

    @GetMapping("/agency")
    fun getAgencyById(@RequestParam(value = "id", required = true) id: Long): ResponseEntity<*> =
        agencyService.getById(id = id).let {
            when (it) {
                is GetByIdResult.NotFound -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it)
                is GetByIdResult.Success -> ResponseEntity.status(HttpStatus.OK).body(it.agencyResponseForm)
            }
        }

    @PostMapping
    fun updateBankAccount(updateBankAccountForm: AgencyUpdateBankAccountForm): ResponseEntity<*> =
        agencyService.updateBankAccount(updateBankAccountForm).let {}
}