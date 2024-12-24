package ru.jetlabs.ts.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Agencies : LongIdTable(name = "agencies") {
    val ownerId = long("ownerId")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
}