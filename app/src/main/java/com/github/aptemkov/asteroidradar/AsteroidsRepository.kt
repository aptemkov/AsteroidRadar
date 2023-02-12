package com.github.aptemkov.asteroidradar

import com.github.aptemkov.asteroidradar.api.AsteroidApi
import com.github.aptemkov.asteroidradar.api.parseAsteroidsJsonResult
import com.github.aptemkov.asteroidradar.database.AsteroidsRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidsRepository (
    private val database: AsteroidsRoomDatabase
) {

    suspend fun  refreshAsteroids(
        startDate: String = today(),
        endDate: String = seventhDay(),
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

private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(date)
}

fun today(): String {
    val calendar = Calendar.getInstance()
    return formatDate(calendar.time)
}

fun seventhDay(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return formatDate(calendar.time)
}

