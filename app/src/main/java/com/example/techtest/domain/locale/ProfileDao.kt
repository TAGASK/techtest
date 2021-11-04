package com.example.techtest.domain.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.techtest.data.entities.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM Profiles")
    fun getUsers(): Flow<List<Profile>>

    @Query("SELECT * FROM Profiles WHERE id = :id")
    fun getUser(id: String): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profiles: List<Profile>)
}