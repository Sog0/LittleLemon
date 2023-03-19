package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomMasterTable

@Entity
data class MenuItem(
    @PrimaryKey val id : Int,
    val title: String,
    val desc: String,
    val price : String,
    val image : String,
    val category: String
)


@Dao
interface MenuDao{
    @Query("SELECT * FROM MenuItem")
    fun getAll() : LiveData<List<MenuItem>>

    @Insert
    fun add(vararg menuItem: MenuItem)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItem) == 0")
    fun isEmpty(): Boolean

    @Query ( "SELECT * FROM MenuItem WHERE category = :category")
    fun filter(category:String) : MenuItem
}

@Database(entities = [MenuItem::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun menuItemDao(): MenuDao
}

