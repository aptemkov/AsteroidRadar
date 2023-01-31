package com.github.aptemkov.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aptemkov.asteroidradar.Asteroid
import com.github.aptemkov.asteroidradar.R
import com.github.aptemkov.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val list = mutableListOf(
            Asteroid(id = 1, codename = "test 1", closeApproachDate = "true", absoluteMagnitude = 1.34, estimatedDiameter = 1.32, relativeVelocity = 12.42, distanceFromEarth = 12.21, isPotentiallyHazardous = true ),
            Asteroid(id = 2, codename = "test 2", closeApproachDate = "false", absoluteMagnitude = 1.34, estimatedDiameter = 1.32, relativeVelocity = 12.42, distanceFromEarth = 12.21, isPotentiallyHazardous = true ),
            Asteroid(id = 3, codename = "test 3", closeApproachDate = "true", absoluteMagnitude = 1.34, estimatedDiameter = 1.32, relativeVelocity = 12.42, distanceFromEarth = 12.21, isPotentiallyHazardous = false ),
            Asteroid(id = 4, codename = "test 4", closeApproachDate = "true", absoluteMagnitude = 1.34, estimatedDiameter = 1.32, relativeVelocity = 12.42, distanceFromEarth = 12.21, isPotentiallyHazardous = true ),
        )

        binding.asteroidRecycler.layoutManager = LinearLayoutManager(this.context)
        val adapter = AsteroidsAdapter(
            object: AsteroidsAdapter.AsteroidListener {
                override fun onDetailInfo(asteroid: Asteroid) {
                    //Toast.makeText(activity?.applicationContext, "$asteroid", Toast.LENGTH_SHORT).show()
                    val action = MainFragmentDirections.actionShowDetail(asteroid)
                    findNavController().navigate(action)
                }

            }
        )
        binding.asteroidRecycler.adapter = adapter
        adapter.submitList(list)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
