package com.github.aptemkov.asteroidradar.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.aptemkov.asteroidradar.Asteroid
import com.github.aptemkov.asteroidradar.R
import com.github.aptemkov.asteroidradar.database.AsteroidsDao
import com.github.aptemkov.asteroidradar.database.AsteroidsRoomDatabase
import com.github.aptemkov.asteroidradar.databinding.FragmentDetailBinding
import com.github.aptemkov.asteroidradar.main.MainViewModel

class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val navigationArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid


        val id = navigationArgs.id
        viewModel.getById(id).observe(viewLifecycleOwner) {
            binding.asteroid = it
        }


        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
