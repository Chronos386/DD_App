package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.Klaxon
import com.example.dd_app.dataSource.CharacterData
import java.io.Serializable

private val klaxon = Klaxon()

class CharactersData(elements: Collection<CharacterData>) : ArrayList<CharacterData>(elements),
    Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = CharactersData(klaxon.parseArray<CharacterData>(json)!!)
    }
}