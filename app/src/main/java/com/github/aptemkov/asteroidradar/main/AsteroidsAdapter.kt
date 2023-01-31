package com.github.aptemkov.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.aptemkov.asteroidradar.Asteroid
import com.github.aptemkov.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidsAdapter(private val listener: AsteroidListener) :
    ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(DiffCallback), View.OnClickListener {

    override fun onClick(v: View?) {
        val asteroid = v?.tag as Asteroid
        listener.onDetailInfo(asteroid)
    }

    interface AsteroidListener {
        fun onDetailInfo(asteroid: Asteroid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAsteroidBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class AsteroidViewHolder(private var binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.root.tag = asteroid

            binding.apply {
                this.asteroid = asteroid
                binding.executePendingBindings()
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }

}