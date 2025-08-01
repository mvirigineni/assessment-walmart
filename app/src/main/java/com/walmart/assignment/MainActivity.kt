package com.walmart.assignment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.walmart.assignment.adapter.CountriesAdapter
import com.walmart.assignment.databinding.ActivityMainBinding
import com.walmart.assignment.ui.UIState
import com.walmart.assignment.viewmodel.CountriesViewModel
import com.walmart.assignment.data.Country

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CountriesViewModel
    private lateinit var adapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupRetryButton()
    }

    private fun setupRecyclerView() {
        adapter = CountriesAdapter()
        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[CountriesViewModel::class.java]
        viewModel.uiState.observe(this) { state ->
            handleUIState(state)
        }
        viewModel.loadCountries()
    }

    private fun setupRetryButton() {
        binding.btnRetry.setOnClickListener {
            viewModel.loadCountries()
        }
    }

    private fun handleUIState(state: UIState<List<Country>>) {
        when (state) {
            is UIState.Loading -> {
                binding.rvCountries.visibility = android.view.View.GONE
                binding.llLoading.visibility = android.view.View.VISIBLE
                binding.llError.visibility = android.view.View.GONE
            }
            is UIState.Success -> {
                binding.rvCountries.visibility = android.view.View.VISIBLE
                binding.llLoading.visibility = android.view.View.GONE
                binding.llError.visibility = android.view.View.GONE
                adapter.updateCountries(state.data)
            }
            is UIState.Error -> {
                binding.rvCountries.visibility = android.view.View.GONE
                binding.llLoading.visibility = android.view.View.GONE
                binding.llError.visibility = android.view.View.VISIBLE
                binding.tvErrorMessage.text = state.message
            }
            is UIState.Empty -> {
                binding.rvCountries.visibility = android.view.View.GONE
                binding.llLoading.visibility = android.view.View.GONE
                binding.llError.visibility = android.view.View.VISIBLE
                binding.tvErrorMessage.text = getString(R.string.error_empty_response)
            }
        }
    }
}