package com.github.aptemkov.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Database
import com.github.aptemkov.asteroidradar.Asteroid
import com.github.aptemkov.asteroidradar.AsteroidApplication
import com.github.aptemkov.asteroidradar.AsteroidsRepository
import com.github.aptemkov.asteroidradar.Constants
import com.github.aptemkov.asteroidradar.Constants.API_KEY
import com.github.aptemkov.asteroidradar.api.AsteroidApi
import com.github.aptemkov.asteroidradar.api.parseAsteroidsJsonResult
import com.github.aptemkov.asteroidradar.database.AsteroidsDao
import com.github.aptemkov.asteroidradar.database.AsteroidsRoomDatabase
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidsRoomDatabase.getDatabase(application)
    private val repository = AsteroidsRepository(database)

    private val _response = MutableLiveData<ArrayList<Asteroid>>()
    val response: LiveData<ArrayList<Asteroid>> = _response

    init {
        viewModelScope.launch {
            _response.value = repository.refreshAsteroids(today(), seventhDay())
        }

    }

    fun getById(id: Long): LiveData<Asteroid> {
        return database.asteroidsDao().getById(id).asLiveData()
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
}

/*
class MainViewModelFactory(private val asteroidsRoomDatabase: AsteroidsRoomDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(asteroidsRoomDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
