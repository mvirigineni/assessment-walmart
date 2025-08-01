package com.walmart.assignment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.walmart.assignment.data.Country
import com.walmart.assignment.databinding.ItemCountryBinding

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    
    private var countries = listOf<Country>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    class CountryViewHolder(private val binding: ItemCountryBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        @SuppressLint("SetTextI18n")
        fun bind(country: Country) {
            binding.apply {
                tvCountryNameRegion.text = "${country.name}, ${country.region}"
                tvCountryCode.text = country.code
                tvCountryCapital.text = country.capital
            }
        }
    }
} 