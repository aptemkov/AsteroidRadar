package com.github.aptemkov.asteroidradar

import com.github.aptemkov.asteroidradar.api.AsteroidApi
import com.github.aptemkov.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.ArrayList
class AsteroidsRepository {

    suspend fun  refreshAsteroids(
        startDate: String = today(),
        endDate: String = seventhDay()
    ): ArrayList<Asteroid> {
        var asteroidList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroidResponseBody: ResponseBody = AsteroidApi.api.getAsteroids(
                startDate, endDate,
                Constants.API_KEY
            )
                .await()
            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
        }
        return asteroidList
    }
}

