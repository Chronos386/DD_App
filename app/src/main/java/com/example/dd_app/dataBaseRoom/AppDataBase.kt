package com.example.dd_app.dataBaseRoom
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dd_app.dataBaseRoom.account.AccountDB
import com.example.dd_app.dataBaseRoom.account.AccountDao

@Database(entities = [AccountDB::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}