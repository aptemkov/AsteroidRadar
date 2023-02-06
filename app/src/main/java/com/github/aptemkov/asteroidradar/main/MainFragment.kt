package com.github.aptemkov.asteroidradar.main

import android.os.Bundle
import android.view.*
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
        ViewModelProvider(this)[MainViewModel::class.java]
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


        binding.asteroidRecycler.layoutManager = LinearLayoutManager(this.context)
        val adapter = AsteroidsAdapter(
            object : AsteroidsAdapter.AsteroidListener {
                override fun onDetailInfo(asteroid: Asteroid) {
                    //Toast.makeText(activity?.applicationContext, "$asteroid", Toast.LENGTH_SHORT).show()
                    val action = MainFragmentDirections.actionShowDetail(asteroid.id)
                    findNavController().navigate(action)
                }

            }
        )
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroidsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.week_list -> {
                viewModel.weekAsteroids()
            }
            R.id.today_asteroids -> {
                viewModel.todayAsteroids()
            }
            else -> {
                viewModel.savedAsteroids()
            }
        }
        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}