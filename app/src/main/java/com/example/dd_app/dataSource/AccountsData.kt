package com.example.dd_app.dataSource
import com.beust.klaxon.Klaxon
import java.io.Serializable

private val klaxon = Klaxon()

class AccountsData(elements: Collection<AccountData>) : ArrayList<AccountData>(elements),
    Serializable {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = AccountsData(klaxon.parseArray<AccountData>(json)!!)
    }
}