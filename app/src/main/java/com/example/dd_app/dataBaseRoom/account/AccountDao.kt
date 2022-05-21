package com.example.dd_app.dataBaseRoom.account
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("SELECT * FROM AccountDB")
    fun getAll(): List<AccountDB>

    @Query("DELETE FROM AccountDB")
    fun nukeTable()

    @Query("SELECT * FROM AccountDB WHERE id = :first")
    fun findByID(first: Int): AccountDB

    @Query("SELECT COUNT(*) FROM AccountDB")
    fun findCount(): Int

    @Insert
    fun insert(genre: AccountDB)

    @Delete
    fun delete(genre: AccountDB)
}