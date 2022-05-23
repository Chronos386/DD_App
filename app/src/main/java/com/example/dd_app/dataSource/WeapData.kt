package com.example.dd_app.dataSource
import com.beust.klaxon.*
import java.io.Serializable

private val klaxon = Klaxon()

class WeapData (
    val damage: Long,
    val id: Long,
    val name: String,
    val price: Long,
    @Json(name = "weap_t_id")
    val weapTID: Long,
    val weight: Long
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)
    companion object {
        fun fromJson(json: String) = klaxon.parse<WeapData>(json)
    }
}