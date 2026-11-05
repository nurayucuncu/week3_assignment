package com.example.week3_assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.week3_assignment.data.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DeviceViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedRoom = MutableStateFlow<String?>(null)

    val uiState: StateFlow<DeviceUiState> = combine(
        repository.devices,
        _searchQuery,
        _selectedRoom
    ) { devices, query, room ->
        DeviceUiState(
            devices = devices.filter { device ->
                (room == null || device.room == room) &&
                        device.name.contains(query.trim(), ignoreCase = true)
            },
            rooms = devices.map { it.room }.distinct(),
            selectedRoom = room,
            totalWatts = devices
                .filter { it.isOnline && it.isActive }
                .sumOf { it.powerWatts }
        )
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DeviceUiState()
        )

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    fun onRoomSelect(room: String?) {
        _selectedRoom.value = room
    }

    fun onToggle(id: Int) {
        viewModelScope.launch { repository.toggleDevice(id) }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DeviceViewModel(DeviceRepository()) as T
            }
        }
    }
}