package com.example.week3_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.week3_assignment.ui.DeviceScreen
import com.example.week3_assignment.ui.DeviceViewModel
import com.example.week3_assignment.ui.theme.Week3_assignmentTheme

class MainActivity : ComponentActivity() {

    private val viewModel: DeviceViewModel by viewModels {
        DeviceViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

            Week3_assignmentTheme {
                DeviceScreen(
                    uiState = uiState,
                    searchQuery = searchQuery,
                    onSearchChange = viewModel::onSearchChange,
                    onRoomSelect = viewModel::onRoomSelect,
                    onToggle = viewModel::onToggle
                )
            }
        }
    }
}