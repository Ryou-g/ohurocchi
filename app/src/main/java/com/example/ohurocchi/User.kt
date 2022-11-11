package com.example.ohurocchi

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey val id: Int,
     @ColumnInfo(name = "name") val user_Name: String,
     @ColumnInfo(name = "money") val money: Int,
     @ColumnInfo(name = "apply_face") val apply_Face: Int,
     @ColumnInfo(name = "apply_background") val apply_Background: Int,
     @ColumnInfo(name = "clean") val clean: Int,
     @ColumnInfo(name = "apply_costume") val apply_Costume: Int,
     @ColumnInfo(name = "favorability") val favorability : Int,
)
