package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.*
import com.example.dd_app.dataSource.RaceData
import java.io.Serializable

private val klaxon = Klaxon()

class RacesData(elements: Collection<RaceData>) : ArrayList<RaceData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = RacesData(klaxon.parseArray<RaceData>(json)!!)
    }
}