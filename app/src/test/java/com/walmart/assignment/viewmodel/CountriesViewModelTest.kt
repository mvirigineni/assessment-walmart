package com.walmart.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.walmart.assignment.data.Country
import com.walmart.assignment.repository.CountriesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CountriesViewModel
    private lateinit var mockRepository: CountriesRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk(relaxed = true)
        viewModel = CountriesViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel should be created successfully`() {
        assertNotNull(viewModel)
        assertNotNull(viewModel.uiState)
    }

    @Test
    fun `loadCountries should emit Loading state initially`() = runTest {
        coEvery { mockRepository.getCountries() } returns Result.success(emptyList())
        
        viewModel.loadCountries()
        
        val currentState = viewModel.uiState.value
        assertTrue(currentState is com.walmart.assignment.ui.UIState.Loading)
    }

    @Test
    fun `loadCountries should emit Success state when repository returns data`() = runTest {
        val countries = listOf(
            Country("United States", "Americas", "US", "Washington, D.C."),
            Country("Canada", "Americas", "CA", "Ottawa")
        )
        
        coEvery { mockRepository.getCountries() } returns Result.success(countries)
        
        viewModel.loadCountries()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val currentState = viewModel.uiState.value
        assertTrue(currentState is com.walmart.assignment.ui.UIState.Success)
        assertEquals(countries, (currentState as com.walmart.assignment.ui.UIState.Success).data)
    }

    @Test
    fun `loadCountries should emit Error state when repository fails`() = runTest {
        coEvery { mockRepository.getCountries() } returns Result.failure(Exception("Network error"))
        
        viewModel.loadCountries()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val currentState = viewModel.uiState.value
        assertTrue(currentState is com.walmart.assignment.ui.UIState.Error)
        assertEquals("Network error. Please check your internet connection.", (currentState as com.walmart.assignment.ui.UIState.Error).message)
    }
} 