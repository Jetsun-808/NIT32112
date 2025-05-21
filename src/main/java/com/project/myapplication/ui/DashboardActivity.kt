package com.project.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.project.myapplication.R
import com.project.myapplication.adaptor.DashboardAdapter
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.util.Result
import com.project.myapplication.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Show toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        // Set progress bar
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        progressBar.visibility = View.VISIBLE

        // Observe login result
        dashboardViewModel.dashboardResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    handleResponse(result.data, recyclerView)
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    //Toast.makeText(this, result.exception.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Error occurred while fetching data.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Fetch keypass from login screen
        val keypass = intent.getStringExtra("keypass")
        var keypassValue = "";
        keypassValue = keypass ?: "error"
        dashboardViewModel.getDashboardData(keypassValue)
    }

    // Handle response from API
    fun handleResponse(response: DashboardResponse, recyclerView: RecyclerView) {

        val items = response.entities;

        val adapter = DashboardAdapter(items) { selectedItem ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("item", selectedItem)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}
