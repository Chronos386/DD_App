package com.example.dd_app.dataBaseRoom.account
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class AccountDB (
    @PrimaryKey val id: Long,
    val login: String,
    val password: String,
    val stat_id: Long
) : Serializable