package com.github.aptemkov.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aptemkov.asteroidradar.Asteroid
import com.github.aptemkov.asteroidradar.AsteroidsRepository
import com.github.aptemkov.asteroidradar.Constants
import com.github.aptemkov.asteroidradar.Constants.API_KEY
import com.github.aptemkov.asteroidradar.api.AsteroidApi
import com.github.aptemkov.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    val repository = AsteroidsRepository()

    private val _response = MutableLiveData<ArrayList<Asteroid>>()
    val response: LiveData<ArrayList<Asteroid>> = _response

    init {
        viewModelScope.launch {
            _response.value = repository.refreshAsteroids(today(), seventhDay())
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