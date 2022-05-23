package com.example.dd_app.dataSource
import com.beust.klaxon.*
import java.io.Serializable

private val klaxon = Klaxon()

data class RaceData (
    @Json(name = "descr_id")
    val descrID: Long,
    val id: Long,
    @Json(name = "incr_char")
    val incrChar: String,
    val name: String,
    @Json(name = "pict_id")
    val pictID: Long,
    val size: String,
    val speed: Long,
    val worldview: String
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<RaceData>(json)
    }
}