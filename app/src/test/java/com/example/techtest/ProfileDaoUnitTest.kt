package com.example.techtest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.techtest.data.entities.Profile
import com.example.techtest.domain.locale.AppDatabase
import com.example.techtest.domain.locale.ProfileDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class ProfileDaoUnitTest {

    private lateinit var database: AppDatabase
    private lateinit var profileDao: ProfileDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        profileDao = database.profileDao()
    }

    @Test
    fun saveProfilesList_retrieveProfilesList() = runBlocking<Unit> {
        val id = "34567FD567"
        val profile = Profile(
            "34567FD567", "Mr", "fred", "lemieux",
            "https://www.picture.com/pic/fezfeiz32"
        )
        profileDao.insertAll(listOf(profile))

        val res = profileDao.getUser(id)
        val resProfile = res.first()
        assertEquals("Mr", resProfile.title)
        assertEquals("fred", resProfile.firstName)
        assertEquals("lemieux", resProfile.lastName)
        assertEquals("https://www.picture.com/pic/fezfeiz32", resProfile.picture)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

}