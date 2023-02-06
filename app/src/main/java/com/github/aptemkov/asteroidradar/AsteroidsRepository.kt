package com.github.aptemkov.asteroidradar

import com.github.aptemkov.asteroidradar.api.AsteroidApi
import com.github.aptemkov.asteroidradar.api.parseAsteroidsJsonResult
import com.github.aptemkov.asteroidradar.database.AsteroidsRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.ArrayList
class AsteroidsRepository (
    private val database: AsteroidsRoomDatabase
) {

    suspend fun  refreshAsteroids(
        startDate: String,
        endDate: String,
    ): ArrayList<Asteroid> {
        var asteroidList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroidResponseBody: ResponseBody = AsteroidApi.api.getAsteroids(
                startDate, endDate,
                Constants.API_KEY
            )
                .await()
            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
            database.asteroidsDao().insertAllAsteroids(*asteroidList.toTypedArray())

        }
        return asteroidList
    }
}

