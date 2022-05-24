package com.example.dd_app.dataSource
import com.beust.klaxon.*
import java.io.Serializable

private val klaxon = Klaxon()

data class ArmorData (
    @Json(name = "arm_t_id")
    val armTID: Long,
    val id: Long,
    val name: String,
    val price: Long,
    @Json(name = "steal_hindr")
    val stealHindr: Boolean,
    val weight: Long
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<ArmorData>(json)
    }
}