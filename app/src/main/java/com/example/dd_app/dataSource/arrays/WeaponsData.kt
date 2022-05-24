package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.Klaxon
import com.example.dd_app.dataSource.WeapData
import java.io.Serializable

private val klaxon = Klaxon()

class WeaponsData(elements: Collection<WeapData>) : ArrayList<WeapData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = WeaponsData(klaxon.parseArray<WeapData>(json)!!)
    }
}