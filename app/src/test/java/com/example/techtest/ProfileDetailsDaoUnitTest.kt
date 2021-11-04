package com.example.techtest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.techtest.data.entities.Location
import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.domain.locale.AppDatabase
import com.example.techtest.domain.locale.ProfileDetailsDao
import dagger.hilt.android.testing.HiltAndroidTest
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
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class ProfileDetailsDaoUnitTest {

    private lateinit var database: AppDatabase
    private lateinit var profileDetailsDao: ProfileDetailsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        profileDetailsDao = database.profileDetailsDao()
    }

    @Test
    fun saveProfileDetails_retrieveProfileDetails() = runBlocking<Unit> {

        var id: String = "34567FD567"
        var title: String = "Mr"
        var firstName: String = "fred"
        var lastName: String = "lemieux"
        var picture: String = "https://www.picture.com:pic/rez43"
        var gender: String = "MALE"
        var email: String = "frederic.lem@gmail.com"
        var dateOfBirth: String = "19/03/1995"
        var phone: String = "054555678"
        var location: Location = Location("et", "du", "du", "uae", "GMT+4")
        var registerDate: String = "19/03/2001"
        var updatedDate: String = "21/03/2022"

        val profileDetails = ProfileDetails(
            id, title, firstName, lastName, picture,
            gender, email, dateOfBirth, phone, location, registerDate, updatedDate
        )
        profileDetailsDao.insert(profileDetails)

        val res = profileDetailsDao.getUser(id)
        val resProfileDetails = res.first()
        assertEquals(title, resProfileDetails.title)
        assertEquals(firstName, resProfileDetails.firstName)
        assertEquals(lastName, resProfileDetails.lastName)
        assertEquals(picture, resProfileDetails.picture)
        assertEquals(gender, resProfileDetails.gender)
        assertEquals(email, resProfileDetails.email)
        assertEquals(dateOfBirth, resProfileDetails.dateOfBirth)
        assertEquals(phone, resProfileDetails.phone)
        assertEquals(location, resProfileDetails.location)
        assertEquals(registerDate, resProfileDetails.registerDate)
        assertEquals(updatedDate, resProfileDetails.updatedDate)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

}