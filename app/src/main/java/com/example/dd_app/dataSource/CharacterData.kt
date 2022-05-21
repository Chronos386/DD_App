package com.example.dd_app.dataSource
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.Serializable

private val klaxon = Klaxon()

data class CharacterData (
    @Json(name = "acc_id")
    val accID: Long,
    var agility: Long,
    @Json(name = "arm_id")
    var armID: Long,
    @Json(name = "body_type")
    var bodyType: Long,
    var charisma: Long,
    @Json(name = "class_id")
    val classID: Long,
    @Json(name = "dmg_buff")
    var dmgBuff: Boolean,
    @Json(name = "game_id")
    val gameID: Long,
    var hp: Long,
    @Json(name = "hp_buff")
    var hpBuff: Boolean,
    val id: Long,
    var intellect: Long,
    val name: String,
    var notes: String,
    @Json(name = "pict_id")
    val pictID: String,
    var power: Long,
    @Json(name = "var_races_id")
    val varRacesID: Long,
    @Json(name = "weap_id")
    val weapID: Long,
    var wisdom: Long
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<CharacterData>(json)
    }
}