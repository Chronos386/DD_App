package com.example.dd_app.dataSource
import com.beust.klaxon.*
import java.io.Serializable

private val klaxon = Klaxon()

data class DescriptionData (
    val field: String,
    val id: Long
) : Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<DescriptionData>(json)
    }
}