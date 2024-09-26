package com.mfs.core.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NutriSwitchDatabaseTest {

    private lateinit var foodDao: FoodDao
    private lateinit var db: NutriSwitchDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = NutriSwitchDatabase.build(context)
        foodDao = db.foodDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun checkDatabaseWasPrepopulatedSuccessful() = runBlocking {
        val foods = foodDao.getAll()
        assertEquals(597, foods.size)
    }
}