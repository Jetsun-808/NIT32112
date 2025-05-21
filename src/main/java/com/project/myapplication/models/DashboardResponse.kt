package com.project.myapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Long,
)
@Parcelize
data class Entity(
    val name: String,
    val culture: String,
    val domain: String,
    val symbol: String,
    val parentage: String,
    val romanEquivalent: String,
    val description: String,
): Parcelable