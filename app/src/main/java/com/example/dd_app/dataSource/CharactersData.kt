package com.example.dd_app.dataSource
import com.beust.klaxon.Klaxon
import java.io.Serializable

private val klaxon = Klaxon()

class CharactersData(elements: Collection<CharacterData>) : ArrayList<CharacterData>(elements),
    Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = CharactersData(klaxon.parseArray<CharacterData>(json)!!)
    }
}