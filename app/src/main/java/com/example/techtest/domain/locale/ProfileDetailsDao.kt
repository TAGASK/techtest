package com.example.techtest.domain.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.techtest.data.entities.ProfileDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDetailsDao {

    @Query("SELECT * FROM ProfilesDetails")
    fun getUsers(): Flow<List<ProfileDetails>>

    @Query("SELECT * FROM ProfilesDetails WHERE id = :id")
    fun getUser(id: String): Flow<ProfileDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileDetails)


}