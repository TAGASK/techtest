package com.example.techtest.data.locale

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.techtest.data.entities.ProfileDetails

@Dao
interface ProfileDetailsDao {

    @Query("SELECT * FROM ProfilesDetails")
    fun getUsers(): LiveData<List<ProfileDetails>>

    @Query("SELECT * FROM ProfilesDetails WHERE id = :id")
    fun getUser(id: Int): LiveData<ProfileDetails>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(profiles: List<ProfileDetails>): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileDetails)


}