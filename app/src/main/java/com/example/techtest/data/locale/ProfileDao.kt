package com.example.techtest.data.locale

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.techtest.data.entities.Profile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM Profiles")
    fun getUsers(): LiveData<List<Profile>>

    @Query("SELECT * FROM Profiles WHERE id = :id")
    fun getUser(id: Int): LiveData<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profiles: List<Profile>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(profile: Profile)
}