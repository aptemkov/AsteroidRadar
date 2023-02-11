package com.github.aptemkov.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.github.aptemkov.asteroidradar.Asteroid
import com.github.aptemkov.asteroidradar.AsteroidsRepository
import com.github.aptemkov.asteroidradar.Constants
import com.github.aptemkov.asteroidradar.PictureOfDay
import com.github.aptemkov.asteroidradar.api.loadPictureOfDay
import com.github.aptemkov.asteroidradar.database.AsteroidsRoomDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidsRoomDatabase.getDatabase(application)
    private val repository = AsteroidsRepository(database)

    private var _asteroidsLiveData = MutableLiveData<List<Asteroid>>()
    val asteroidsLiveData: LiveData<List<Asteroid>> = _asteroidsLiveData

    private val _pictureOfDayLiveData = MutableLiveData<PictureOfDay?>()
    val pictureOfDayLiveData: LiveData<PictureOfDay?>
        get() = _pictureOfDayLiveData

    init {
        viewModelScope.launch {
            _asteroidsLiveData.value = repository.refreshAsteroids(today(), seventhDay())
            updatePictureOfDay()
        }
    }

    private suspend fun updatePictureOfDay() {
        _pictureOfDayLiveData.value = loadPictureOfDay()

        Log.i("TEST", "MainViewModel:${pictureOfDayLiveData.value}")
    }

    fun getById(id: Long): LiveData<Asteroid> {
        return database.asteroidsDao().getById(id).asLiveData()
    }

    fun todayAsteroids(){
        viewModelScope.launch {
            _asteroidsLiveData.value = repository.refreshAsteroids(today(), today())
        }
    }
    fun weekAsteroids(){
        viewModelScope.launch {
            _asteroidsLiveData.value = repository.refreshAsteroids(today(), seventhDay())
        }
    }
    fun savedAsteroids(){
        viewModelScope.launch {
            _asteroidsLiveData = database.asteroidsDao().getAllAsteroids().asLiveData() as MutableLiveData<List<Asteroid>>
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
}
