package ru.jetlabs.ts.userservice.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.jetlabs.ts.userservice.models.*
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

    @GetMapping("/agency/{id}")
    fun getAgencyById(@PathVariable id: Long): ResponseEntity<*> =
        agencyService.getById(id = id).let {
            when (it) {
                is GetByIdResult.NotFound -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it)
                is GetByIdResult.Success -> ResponseEntity.status(HttpStatus.OK).body(it.agencyResponseForm)
            }
        }

    @GetMapping("/agency/my/{id}")
    fun getMyAgencies(@PathVariable id: Long) : ResponseEntity<*>{
        return agencyService.getByOwnerId(ownerId = id).let {
            when(it) {
                GetByOwnerIdResult.NotFound -> ResponseEntity.badRequest().body(it)
                is GetByOwnerIdResult.Success -> ResponseEntity.ok(it.agencies)
            }
        }
    }

    @PostMapping("/update-bank")
    fun updateBankAccount(@RequestBody updateBankAccountForm: AgencyUpdateBankAccountForm): ResponseEntity<*> =
        agencyService.updateBankAccount(updateBankAccountForm).let {
            when(it){
                AgencyUpdateBankAccountResult.Success -> ResponseEntity.ok("Success")
                is AgencyUpdateBankAccountResult.UnknownError -> ResponseEntity.badRequest().body("Bad")
            }
        }
}