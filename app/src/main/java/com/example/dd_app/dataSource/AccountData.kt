package com.example.dd_app.dataSource
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.Serializable
private val klaxon = Klaxon()

data class AccountData (
    val id: Long,
    val login: String,
    var password: String,

    @Json(name = "stat_id")
    val statID: Long
    ) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<AccountData>(json)
    }
}