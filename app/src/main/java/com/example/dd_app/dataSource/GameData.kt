package com.example.dd_app.dataSource
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.Serializable

private val klaxon = Klaxon()

data class GameData (
    @Json(name = "game_name")
    val gameName: String,
    val id: Long,
    @Json(name = "master_id")
    val masterID: String,
    val password: String,
    @Json(name = "world_name")
    val worldName: String
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<GameData>(json)
    }
}