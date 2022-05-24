package com.example.dd_app.dataSource.arrays
import com.beust.klaxon.Klaxon
import com.example.dd_app.dataSource.ClassData
import java.io.Serializable

private val klaxon = Klaxon()

class ClassesData(elements: Collection<ClassData>) : ArrayList<ClassData>(elements), Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = ClassesData(klaxon.parseArray<ClassData>(json)!!)
    }
}