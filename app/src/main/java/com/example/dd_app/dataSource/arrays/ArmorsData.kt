package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.Klaxon
import com.example.dd_app.dataSource.ArmorData
import java.io.Serializable

private val klaxon = Klaxon()

class ArmorsData(elements: Collection<ArmorData>) : ArrayList<ArmorData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = ArmorsData(klaxon.parseArray<ArmorData>(json)!!)
    }
}