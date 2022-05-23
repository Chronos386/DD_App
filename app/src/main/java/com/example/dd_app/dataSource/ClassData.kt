package com.example.dd_app.dataSource
import com.beust.klaxon.*
import java.io.Serializable

private val klaxon = Klaxon()

class ClassData (
    @Json(name = "descr_id")
    val descrID: Long,
    val id: Long,
    @Json(name = "master_bonus")
    val masterBonus: Long,
    val name: String,
    @Json(name = "numb_av_spells")
    val numbAVSpells: Long,
    @Json(name = "pict_id")
    val pictID: Long
) : Serializable {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<ClassData>(json)
    }
}