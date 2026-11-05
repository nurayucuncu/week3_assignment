package com.example.week3_assignment.data

import com.example.week3_assignment.model.DeviceCategory.*
import com.example.week3_assignment.model.DeviceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class DeviceRepository {
    private val _devices = MutableStateFlow(
        listOf(
            DeviceItem(1, "Köşe Lambası", LIGHT, "Salon", true, true, 15.0),
            DeviceItem(2, "Tavan Avizesi", LIGHT, "Salon", true, false, 60.0),
            DeviceItem(3, "Bahçe Kamerası", CAMERA, "Bahçe", true, true, 8.5),
            DeviceItem(4, "Giriş Kamerası", CAMERA, "Antre", true, true, 8.5),
            DeviceItem(5, "Merkezi Termostat", THERMOSTAT, "Salon", true, true, 12.0),
            DeviceItem(6, "Yatak Odası Kliması", THERMOSTAT, "Yatak Odası", true, false, 1200.0),
            DeviceItem(7, "Mutfak Spotları", LIGHT, "Mutfak", true, false, 45.0),
            DeviceItem(8, "Roborock S7", VACUUM, "Koridor", true, true, 50.0),
            DeviceItem(9, "Balkon Aydınlatması", LIGHT, "Balkon", false, false, 25.0),
            DeviceItem(10, "Çalışma Odası Lambası", LIGHT, "Ofis", true, true, 12.0)
        )
    )
    val devices: StateFlow<List<DeviceItem>> = _devices.asStateFlow()

    suspend fun toggleDevice(id: Int) = withContext(Dispatchers.Default) {
        _devices.update { list ->
            list.map { device ->
                if (device.id == id && device.isOnline) {
                    device.copy(isActive = !device.isActive)
                } else {
                    device
                }
            }
        }
    }
}