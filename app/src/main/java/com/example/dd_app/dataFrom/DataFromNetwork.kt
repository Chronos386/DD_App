package com.example.dd_app.dataFrom
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class DataFromNetwork {
    private val client = OkHttpClient()
    private var request = Request.Builder()
    var str: String = ""

    fun getAllClasses() {
        request.url("http://hyrule.ru/table/class")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getAllRaces() {
        request.url("http://hyrule.ru/table/race")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getAllVarRacesByRaceID(raceID: Long) {
        request.url("http://hyrule.ru/types/var_race/$raceID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getAllWeapByClassID(classID: Long) {
        request.url("http://hyrule.ru/class/weapon/$classID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getAllArmByClassID(classID: Long) {
        request.url("http://hyrule.ru/class/armor/$classID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun checkAccountExist (name: String){
        request.url("http://hyrule.ru/exist/account/$name")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun checkGameExist (name: String){
        request.url("http://hyrule.ru/exist/game/$name")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getAccountInf(log: String, psw: String) {
        request.url("http://hyrule.ru/table/account/$log/$psw")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getGameInf(name: String, psw: String) {
        request.url("http://hyrule.ru/table/game/$name/$psw")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getCharactersByAccount(id: Long) {
        request.url("http://hyrule.ru/account/characters/$id")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getCharactersByAccAndGame(accID: Long, gameID: Long) {
        request.url("http://hyrule.ru/table/character/$accID/$gameID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getGamesByAccount(id: Long) {
        request.url("http://hyrule.ru/account/games/$id")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getAccountsByGame(gameID: Long) {
        request.url("http://hyrule.ru/game/accounts/$gameID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getSpellsByClass(classID: Long) {
        request.url("http://hyrule.ru/class/spell/$classID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun addNewAccount(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/new/account")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun updAccountPsw(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/update/account")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun dellAccount(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/delete/account")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun dellCharacter(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/delete/character")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun addCharacter(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/new/character")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun redactCharacter(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/update/character")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun addNewGame(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/new/game")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun updGameByDelCh(gameId: Long, accId: Long) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = accId.toString().toRequestBody(mediaType)
        request.url("http://hyrule.ru/update/game/$gameId")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun dellGame(body: String) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        request.url("http://hyrule.ru/delete/game")
            .post(requestBody)
        client.newCall(request.build()).execute().close()
        request = Request.Builder()
    }

    fun getVarRaceByID(varRaceID: Long) {
        request.url("http://hyrule.ru/table/var_race/$varRaceID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getRaceByID(raceID: Long) {
        request.url("http://hyrule.ru/table/race/$raceID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getPictByID(pictID: Long) {
        request.url("http://hyrule.ru/table/picture/$pictID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        request = Request.Builder()
    }

    fun getClassByID(classID: Long) {
        request.url("http://hyrule.ru/table/class/$classID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getDescrByID(descID: Long) {
        request.url("http://hyrule.ru/table/descript/$descID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getWeapByID(weapID: Long) {
        request.url("http://hyrule.ru/table/weap/$weapID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }

    fun getArmorByID(armID: Long) {
        request.url("http://hyrule.ru/table/armor/$armID")
        client.newCall(request.build()).execute().use { response -> str = response.body!!.string() }
        str = str.replace("[", "")
        str = str.replace("]", "")
        request = Request.Builder()
    }
}