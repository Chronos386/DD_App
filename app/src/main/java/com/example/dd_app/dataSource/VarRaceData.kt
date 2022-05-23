package com.example.dd_app.dataSource
import com.beust.klaxon.*
import java.io.Serializable

private val klaxon = Klaxon()

data class VarRaceData (
    @Json(name = "add_feat")
    val addFeat: String,
    @Json(name = "descr_id")
    val descrID: Long,
    val id: Long,
    @Json(name = "incr_char")
    val incrChar: String,
    val name: String,
    @Json(name = "rac_id")
    val racID: Long
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<VarRaceData>(json)
    }
}