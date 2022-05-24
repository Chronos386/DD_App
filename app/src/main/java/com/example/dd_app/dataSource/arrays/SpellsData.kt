package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.*
import com.example.dd_app.dataSource.SpellData
import java.io.Serializable

private val klaxon = Klaxon()

class SpellsData(elements: Collection<SpellData>) : ArrayList<SpellData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = SpellsData(klaxon.parseArray<SpellData>(json)!!)
    }
}