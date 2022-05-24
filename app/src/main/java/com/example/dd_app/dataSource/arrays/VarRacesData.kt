package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.Klaxon
import com.example.dd_app.dataSource.VarRaceData
import java.io.Serializable

private val klaxon = Klaxon()

class VarRacesData(elements: Collection<VarRaceData>) : ArrayList<VarRaceData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = VarRacesData(klaxon.parseArray<VarRaceData>(json)!!)
    }
}