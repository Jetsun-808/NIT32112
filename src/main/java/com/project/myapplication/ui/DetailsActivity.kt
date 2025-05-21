package com.project.myapplication.ui

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.project.myapplication.R
import com.project.myapplication.models.Entity
import com.project.myapplication.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Show toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.title = "Profile"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Fetch selected item from dashboard
        val item: Entity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", Entity::class.java)
        } else {
            intent.getParcelableExtra<Entity>("item")
        }

        item?.let {
            // Populate the item data to populate your UI
            findViewById<TextView>(R.id.tvName).text = item.name
            findViewById<TextView>(R.id.tvCulture).text = item.culture
            findViewById<TextView>(R.id.tvDomain).text = item.domain
            findViewById<TextView>(R.id.tvSymbol).text = item.symbol
            findViewById<TextView>(R.id.tvParentage).text = item.parentage
            findViewById<TextView>(R.id.tvRomanEquivalent).text = item.romanEquivalent
            findViewById<TextView>(R.id.tvDescription).text = item.description
        }
    }
}

