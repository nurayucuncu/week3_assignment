package com.example.week3_assignment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week3_assignment.model.DeviceItem

@Composable
fun DeviceScreen(
    uiState: DeviceUiState,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onRoomSelect: (String?) -> Unit,
    onToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EnergyCard(uiState.totalWatts)

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                label = { Text("Cihaz ara") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            RoomFilters(uiState.rooms, uiState.selectedRoom, onRoomSelect)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = uiState.devices, key = { it.id }) { device ->
                    DeviceCard(device, onToggle)
                }
            }
        }
    }
}

@Composable
private fun EnergyCard(totalWatts: Double, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Anlık Toplam Tüketim", style = MaterialTheme.typography.titleMedium)
            Text("$totalWatts W", style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Composable
private fun RoomFilters(
    rooms: List<String>,
    selectedRoom: String?,
    onRoomSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedRoom == null,
                onClick = { onRoomSelect(null) },
                label = { Text("Tümü") }
            )
        }
        items(items = rooms, key = { it }) { room ->
            FilterChip(
                selected = selectedRoom == room,
                onClick = { onRoomSelect(room) },
                label = { Text(room) }
            )
        }
    }
}

@Composable
private fun DeviceCard(
    device: DeviceItem,
    onToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(device.name, style = MaterialTheme.typography.titleMedium)
                Text("${device.room} • ${device.category} • ${device.powerWatts} W")
                Text(
                    when {
                        !device.isOnline -> "Çevrimdışı"
                        device.isActive -> "Açık"
                        else -> "Kapalı"
                    }
                )
            }
            Button(
                onClick = { onToggle(device.id) },
                enabled = device.isOnline
            ) {
                Text(if (device.isActive) "Kapat" else "Aç")
            }
        }
    }
}