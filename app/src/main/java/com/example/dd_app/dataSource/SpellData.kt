package com.example.dd_app.dataSource
import com.beust.klaxon.*

private val klaxon = Klaxon()

data class SpellData (
    @Json(name = "descr_id")
    val descrID: Long,
    val distance: Long,
    val duration: Long,
    val id: Long,
    val level: Long,
    val name: String
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<SpellData>(json)
    }
}