package com.example.week3_assignment.ui

import androidx.compose.runtime.Immutable
import com.example.week3_assignment.model.DeviceItem

@Immutable
data class DeviceUiState(
    val devices: List<DeviceItem> = emptyList(),
    val rooms: List<String> = emptyList(),
    val selectedRoom: String? = null, // null = Tümü
    val totalWatts: Double = 0.0
)