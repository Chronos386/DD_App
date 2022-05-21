package com.example.dd_app.dataFrom
import android.content.Context
import androidx.room.Room
import com.example.dd_app.dataBaseRoom.AppDataBase
import com.example.dd_app.dataBaseRoom.account.AccountDB
import com.example.dd_app.dataSource.AccountData

class DataFromDB {
    private lateinit var myData: AppDataBase

    fun initDataBase(cont: Context) {
        myData = Room.databaseBuilder(cont, AppDataBase::class.java, "myDataBase")
            .build()
    }

    fun findAccount(): List<AccountDB> {
        return myData.accountDao().getAll()
    }

    fun findCountAccount(): Int {
        return myData.accountDao().findCount()
    }

    fun addNewAccount(acc: AccountData) {
        val accDB = AccountDB(acc.id, acc.login, acc.password, acc.statID)
        myData.accountDao().insert(accDB)
    }

    fun clearAccountTable() {
        myData.accountDao().nukeTable()
    }
}