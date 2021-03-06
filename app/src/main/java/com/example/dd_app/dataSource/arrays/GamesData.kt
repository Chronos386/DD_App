package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.*
import com.example.dd_app.dataSource.GameData
import java.io.Serializable

private val klaxon = Klaxon()

class GamesData(elements: Collection<GameData>) : ArrayList<GameData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = GamesData(klaxon.parseArray<GameData>(json)!!)
    }
}